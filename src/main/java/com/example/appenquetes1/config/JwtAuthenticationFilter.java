// com.example.appenquetes1.config.JwtAuthenticationFilter.java
package com.example.appenquetes1.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        String path = request.getRequestURI();
        boolean isPublic = path.startsWith("/api/auth/") || path.startsWith("/api/survey/") || path.equals("/api/answers/submit");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);
                Integer userId = jwtUtil.extractUserId(token);

                String authority = role.toUpperCase();
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));

                UserDetails userDetails = new User(username, "", authorities);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                //stocker userId pour save
                request.setAttribute("userId", userId);
                System.out.println("Token valide");
                filterChain.doFilter(request, response);
                return;

            } else {
                System.out.println("Token invalide :" + token);
                if (!isPublic) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide ou expiré");
                    return;
                }
            }
        } else if (!isPublic) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentification requise");
            return;

        }
        System.out.println("PUBLIC URL : " + path);
        filterChain.doFilter(request, response);


    }
}