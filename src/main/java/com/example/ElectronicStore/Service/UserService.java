package com.example.ElectronicStore.Service;

import com.example.ElectronicStore.Dto.UserDto;
import com.example.ElectronicStore.Entity.User;
import com.example.ElectronicStore.Repository.UserRepository;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.GenricPageableResponse;
import com.example.ElectronicStore.Utils.PageableResponse;
import com.example.ElectronicStore.Utils.SpringContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    public UserService(UserRepository userRepository){
        this.userRepository  = userRepository;
    }

    public UserDto createUser(User user){
        return mapper.convertValue(userRepository.save(user),UserDto.class);
    }

    public UserDto getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No Such user exist with given email id"));
        return mapper.convertValue(user,UserDto.class);
    }

    public ApiResponseMessage deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No Such user exist with given  id"));
        userRepository.deleteById(userId);
        return ApiResponseMessage.builder().message("User Deleted Successfully").status(HttpStatus.OK).success(true).build();
    }

    public UserDto getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No Such user exist with given id"));
        return mapper.convertValue(user,UserDto.class);
    }

    public PageableResponse<UserDto> getAllusers(int pageNumber, int pageSize,String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = GenricPageableResponse.getPageableResponse(page, UserDto.class);
        return response;
    }


}
