package com.shopapp.shopapp.util;

import com.shopapp.shopapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration; // save to environment variable
    @Value("${jwt.secretKey}")
    private String secretKey;
    public String generateToken(User user){
        //properties =>claims
        Map<String,Object> claims = new HashMap<>();
        claims.put("phoneNumber",user.getPhoneNumber());
        try{
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration*1000L))
                    .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    private Claims extractAllClaims(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims =this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private Key getSignInkey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    public String extractPhoneNumber(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public boolean isValidToken(String token, UserDetails userDetails){
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }
}
