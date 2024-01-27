package com.example.ElectronicStore.Config;

import com.example.ElectronicStore.Entity.User;
import com.example.ElectronicStore.Repository.UserRepository;
import com.example.ElectronicStore.Service.JwtService;
import com.example.ElectronicStore.Service.UserService;
import com.example.ElectronicStore.Utils.NullUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService,UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(NullUtils.isNull(authHeader) || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUser(jwt);
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("No Such user present"));
            Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
            if(jwtService.isTokenValid(jwt,user)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        authorities
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toString());
        return List.of(authority);
    }
}
