package com.example.ElectronicStore.Config;

import com.example.ElectronicStore.Entity.User;
import com.example.ElectronicStore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;
    public CustomAuthenticationProvider(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String password = authentication.getCredentials().toString();
        String email = authentication.getName();
        String role = authentication.getAuthorities().toString();
        System.out.println(role);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = this.userRepository.findByEmail(email).orElseThrow(()->new BadCredentialsException("No such user found"));
        if(user==null || !email.equalsIgnoreCase(user.getEmail())){
            throw new BadCredentialsException("Username not found.");
        }
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Username not found.");
        }

        return new UsernamePasswordAuthenticationToken(user,password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
