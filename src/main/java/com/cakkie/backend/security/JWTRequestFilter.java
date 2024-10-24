package com.cakkie.backend.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.repository.UserSiteRepository;
import com.cakkie.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserSiteRepository userSiteRepository;

    public JWTRequestFilter(JwtService jwtService, UserSiteRepository userSiteRepository) {
        this.jwtService = jwtService;
        this.userSiteRepository = userSiteRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try{
                String email = jwtService.getEmail(token);
                Optional<userSite> opUser = userSiteRepository.findByEmailIgnoreCase(email);
                if(opUser.isPresent()) {
                    userSite user = opUser.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException e) {

            }
        }
        filterChain.doFilter(request, response);
    }
}
