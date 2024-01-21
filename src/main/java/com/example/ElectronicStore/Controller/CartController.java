package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.CartDto;
import com.example.ElectronicStore.Service.CartService;
import com.example.ElectronicStore.Utils.AddCartRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
