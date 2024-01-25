package com.example.ElectronicStore.Service;

import com.example.ElectronicStore.Dto.CartDto;
import com.example.ElectronicStore.Dto.CategoryDto;
import com.example.ElectronicStore.Dto.OrderDto;
import com.example.ElectronicStore.Entity.*;
import com.example.ElectronicStore.Repository.CartRepository;
import com.example.ElectronicStore.Repository.OrdersRepository;
import com.example.ElectronicStore.Repository.UserRepository;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.GenricPageableResponse;
import com.example.ElectronicStore.Utils.OrderRequestBody;
import com.example.ElectronicStore.Utils.PageableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private CartRepository cartRepository;

    private OrdersRepository ordersRepository;

    private ObjectMapper mapper = new ObjectMapper();
    private final UserRepository userRepository;

    public OrderService(CartRepository cartRepository, OrdersRepository ordersRepository,
                        UserRepository userRepository){
        this.cartRepository = cartRepository;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    // create an order
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto createNewOrder(OrderRequestBody orderBody){
        Orders order = new Orders();
        Long cartId = orderBody.getCartId();
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new RuntimeException("No suh cart present"));
        AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);
        List<OrderItems> orderItemsList = cart.getCartItem().stream().map(cartItem->{
            Product product = cartItem.getProduct();
            double price = product.getPrice();
            int quantity = cartItem.getQuantity();
            totalAmount.accumulateAndGet(price*quantity,(a,b)->a+b);
            return OrderItems.builder().orders(order).price(price*quantity).quantity(quantity).product(product).build();
        }).collect(Collectors.toList());

        order.setOrderedDate(new Date());
        order.setOrderItemsList(orderItemsList);
        order.setOrderStatus("Pending");
        order.setBillingAddress(orderBody.getBillingAddress());
        order.setTotalPrice(totalAmount.get());
        order.setUser(cart.getUser());
        order.setBillingName(orderBody.getBillingName());
        order.setTotalQuantity(cart.getTotalQuantity());
        order.setBillingNumber(orderBody.getBillingNumber());
        Orders orderSaved = ordersRepository.save(order);
        cartRepository.delete(cart);
        return mapper.convertValue(orderSaved,OrderDto.class);
    }

    // delete an order
    public ApiResponseMessage deleteOrder(Long orderId) {
        try {
            Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No such order exist"));
            ordersRepository.delete(order);
            return ApiResponseMessage.builder().message("Order deleted Successfully").success(true).status(HttpStatus.OK).build();
        }
        catch (Exception ex){
            throw  new RuntimeException(ex.getMessage());
        }
    }

    // get all orders of an user
    public PageableResponse<OrderDto> getAllOrderOfUser(Long userId,int pageNo, int pageSize,String sortBy, String sortDir){
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No such user exist"));
        Page<Orders> userOrders = ordersRepository.findByUser(user, pageable);
        PageableResponse<OrderDto> response = GenricPageableResponse.getPageableResponse(userOrders, OrderDto.class);
        return response;
    }

    // get an order
    public OrderDto getOrderById(Long orderId){
        Orders order = ordersRepository.findById(orderId).orElseThrow(()->new RuntimeException("No such Order exist"));
        return mapper.convertValue(order,OrderDto.class);
    }


}
