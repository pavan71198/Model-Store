package com.example.modelstore.util.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.modelstore.service.auth.AppUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private AppUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwtToken = authHeader.substring(7);
            if (jwtToken.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Authorization Header");
            }
            else{
                try{
                    String id = jwtUtil.validateToken(jwtToken);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(id);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                catch (JWTVerificationException exception){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Authorization Token");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
