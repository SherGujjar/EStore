package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.UserDto;
import com.example.ElectronicStore.Entity.User;
import com.example.ElectronicStore.Service.JwtService;
import com.example.ElectronicStore.Service.UserService;
import com.example.ElectronicStore.Utils.AuthResponse;
import com.example.ElectronicStore.Utils.LoginRequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/auth")
@RestController()
public class AuthenticationController {

    private UserService userService;

    private JwtService jwtService;

    public AuthenticationController(UserService userService,JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user){
        UserDto userDto = userService.createUser(user);
        String jwtToken = jwtService.generateToken(userDto);
        AuthResponse response = new AuthResponse(userDto,jwtToken);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping(path="/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestBody lrb){
        AuthResponse authenticate = userService.authenticate(lrb);
        return new ResponseEntity<>(authenticate,HttpStatus.OK);
    }


}
