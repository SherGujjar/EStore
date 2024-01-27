package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.UserDto;
import com.example.ElectronicStore.Entity.User;
import com.example.ElectronicStore.Service.UserService;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.PageableResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PageableResponse<UserDto> getAllUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
                                                 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return userService.getAllusers(pageNumber,pageSize,sortBy,sortDir);
    }

    @GetMapping(path = "/userById/{id}")
    public UserDto getUserById(@PathVariable String id){
        return userService.getUserById(Long.parseLong(id));
    }

    @GetMapping(path="/userByEmail")
    public UserDto getUserByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }


    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable Long id){
        ApiResponseMessage msg =  userService.deleteUser(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


}
