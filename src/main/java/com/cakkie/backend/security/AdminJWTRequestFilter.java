package com.cakkie.backend.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.cakkie.backend.model.admin;
import com.cakkie.backend.repository.AdminLoginRepo;
import com.cakkie.backend.service.AdminJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class AdminJWTRequestFilter extends OncePerRequestFilter {
    private AdminJwtService adminJwtService;
    private AdminLoginRepo userSiteRepository;

    public AdminJWTRequestFilter(AdminJwtService adminJwtService, AdminLoginRepo userSiteRepository) {
        this.adminJwtService = adminJwtService;
        this.userSiteRepository = userSiteRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try{
                String username = adminJwtService.getUsername(token);
                Optional<admin> opAdmin = userSiteRepository.findByEmailIgnoreCase(username);
                if(opAdmin.isPresent()) {
                    admin user = opAdmin.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource
                            ().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException e) {

            }
        }
        filterChain.doFilter(request, response);
    }
}
