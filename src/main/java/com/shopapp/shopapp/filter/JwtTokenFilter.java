package com.shopapp.shopapp.filter;

import com.shopapp.shopapp.model.User;
import com.shopapp.shopapp.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

            if(isByPassToken(request)){
                filterChain.doFilter(request,response);
                return;
            }

            final String authHeader= request.getHeader("Authorization");
            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UNAUTHORIZED");
                return;
            }
                String token = authHeader.substring(7);
                final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
                if(phoneNumber!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                    User userDetails = (User)userDetailsService.loadUserByUsername(phoneNumber);
                    if(jwtTokenUtil.isValidToken(token,userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken=
                                new UsernamePasswordAuthenticationToken(userDetails
                                        ,null,
                                        userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

            }
            filterChain.doFilter(request,response);


    }
    private boolean isByPassToken (@NonNull HttpServletRequest request){
        final List<Pair<String,String>> bypassToken = Arrays.asList(
                Pair.of("/api/v1/products","GET"),
                Pair.of("/api/v1/categories","GET"),
                Pair.of("/api/v1/users/login","POST"),
                Pair.of("/api/v1/users/register","POST")
        );
        for (Pair<String,String> byPass : bypassToken){
            if(request.getServletPath().contains(byPass.getLeft())&&
                    request.getMethod().contains(byPass.getRight())){
                return true;
            }
        }
        return false;
    }

}
