package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.CartDto;
import com.example.ElectronicStore.Entity.CartItem;
import com.example.ElectronicStore.Service.CartService;
import com.example.ElectronicStore.Utils.AddCartRequest;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/v1/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping()
    public ResponseEntity<CartDto> addToCart(@RequestBody List<AddCartRequest> cartBody){
        Long userId = 52L;
        CartDto cartDto = cartService.addItemToCart(userId, cartBody);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);

    }

    @DeleteMapping()
    public ResponseEntity<ApiResponseMessage> deleteCart(@RequestParam Long cartId){
        ApiResponseMessage apiResponseMessage = cartService.deleteCart(cartId);
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<CartDto> deleteItemFromCart(@RequestParam Long cartId, @RequestParam List<Long> productIds){
        CartDto response = cartService.deleteItemFromCart(cartId,productIds);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam Long cartId){
        List<CartItem> allCartItem = cartService.getAllCartItem(cartId);
        return new ResponseEntity<>(allCartItem,HttpStatus.OK);
    }
}
