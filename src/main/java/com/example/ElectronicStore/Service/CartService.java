package com.example.ElectronicStore.Service;

import com.example.ElectronicStore.Dto.CartDto;
import com.example.ElectronicStore.Entity.Cart;
import com.example.ElectronicStore.Entity.CartItem;
import com.example.ElectronicStore.Entity.Product;
import com.example.ElectronicStore.Entity.User;
import com.example.ElectronicStore.Repository.CartItemRepository;
import com.example.ElectronicStore.Repository.CartRepository;
import com.example.ElectronicStore.Repository.ProductRepository;
import com.example.ElectronicStore.Repository.UserRepository;
import com.example.ElectronicStore.Utils.AddCartRequest;
import com.example.ElectronicStore.Utils.NullUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartService {

    private UserRepository userRepository;

    private ProductRepository productRepository;

    private CartRepository cartRepository;

    private CartItemRepository cartItemRepository;

    private ObjectMapper mapper = new ObjectMapper();;

    public CartService(UserRepository userRepository,ProductRepository productRepository,CartRepository cartRepository,
                       CartItemRepository cartItemRepository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // add item to cart
    @Transactional()
    public CartDto addItemToCart(Long userId, List<AddCartRequest> requestBody){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No such user exist in the db"));
        Cart cart = cartRepository.findByUser(user);

        // adding item to cart for the first time
        if(NullUtils.isNull(cart)){
            Cart newCart = new Cart();
            AtomicInteger quantity = new AtomicInteger(0);
            AtomicReference<Double> amount = new AtomicReference<>(0.0);
            List<CartItem> cartItems = requestBody.stream().map(eachCart -> {
                Product productInfo = productRepository.findById(eachCart.getProductId()).orElseThrow(() -> new RuntimeException("No such product presnt"));
                int qty = eachCart.getQty();
                double price = productInfo.getPrice();
                quantity.addAndGet(qty);
                amount.accumulateAndGet(price * qty, (a, b) -> a + b);
                return CartItem.builder().quantity(eachCart.getQty()).cart(newCart).product(productInfo).build();
            }).collect(Collectors.toList());

            newCart.setCartItem(cartItems);
            newCart.setUser(user);
            newCart.setTotalQuantity(quantity.get());
            newCart.setAmount(amount.get());
            Cart cartSaved = cartRepository.save(newCart);
            return mapper.convertValue(cartSaved,CartDto.class);
        }
        else{
            requestBody.stream().forEach(eachCart->{
                Long productId = eachCart.getProductId();
                // check is this productid is already presnt in cart
                // if yes than update its value rather than creating new entry
                AtomicInteger flag = new AtomicInteger(0);
                cart.getCartItem().forEach(eachItem->{
                    if(eachItem.getProduct().getId().equals(productId)){
                        double price = eachItem.getProduct().getPrice();
                        flag.getAndSet(1);
                        // update cartQuantity if the we increase the qty of already existing qty
                        cart.setTotalQuantity(cart.getTotalQuantity()+calculateDiff(eachCart.getQty(),eachItem.getQuantity()));
                        cart.setAmount(cart.getAmount()+calculateDiff(eachCart.getQty(),eachItem.getQuantity())*price);
                        // update the current value
                        eachItem.setQuantity(eachCart.getQty());
                    }
                });
                //  else create new entry
                Product productInfo = productRepository.findById(eachCart.getProductId()).orElseThrow(() -> new RuntimeException("No such product presnt"));
                if(flag.get()==0){
                    cart.getCartItem().add(CartItem.builder().quantity(eachCart.getQty()).cart(cart).product(productInfo).build());
                    cart.setTotalQuantity(cart.getTotalQuantity()+ eachCart.getQty());
                    cart.setAmount(cart.getAmount()+eachCart.getQty()*productInfo.getPrice());
                }
            });


            // delete item if the quantity of any item reduced to zero;
            Iterator<CartItem> iterator = cart.getCartItem().iterator();
            while (iterator.hasNext()) {
                CartItem eachCartItem = iterator.next();
                if (eachCartItem.getQuantity() == 0) {
                    iterator.remove(); // Safely remove the element using the iterator
                }
            }
//            cart.setCartItem(cart.getCartItem().stream().filter(eachCartItem->{
//                return eachCartItem.getQuantity()!=0;
//            }).collect(Collectors.toList()));

            Cart cartSaved = cartRepository.save(cart);
            cartRepository.flush();
            return mapper.convertValue(cartSaved,CartDto.class);
        }
    }

    private int calculateDiff(int currentQty, int previousQty){
        return currentQty-previousQty;
    }

    // remove item from the cart

    // delete all item from the cart

}
