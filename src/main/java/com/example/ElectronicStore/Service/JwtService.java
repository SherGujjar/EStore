package com.example.ElectronicStore.Service;

import com.example.ElectronicStore.Dto.UserDto;
import com.example.ElectronicStore.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "26452948404D635166546A576E5A7234743777217A25432A462D4A614E645267";

    public String generateToken(UserDto user){
        return generateToken(user,new HashMap<>());
    }

    private String generateToken(UserDto user, Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignWith())
                .compact();
    }

    private Key getSignWith(){
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public boolean isTokenValid(String token, User user){
        final String email = extractUser(token);
        return (email.equals(user.getEmail())) && !isTokenExpired(token);
    }

    public String extractUser(String token){
        return extractClaim(token, Claims::getSubject);

    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims,T> cliamResolver){
        final Claims claims = extractAllClaims(token);
        return cliamResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignWith())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
