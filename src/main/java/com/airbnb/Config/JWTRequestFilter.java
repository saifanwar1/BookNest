package com.airbnb.Config;

import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.JWTservice;
import com.auth0.jwt.interfaces.Header;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter{

    private JWTservice jwTservice;
    private PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JWTservice jwTservice, PropertyUserRepository propertyUserRepository) {
        this.jwTservice = jwTservice;
        this.propertyUserRepository = propertyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer")) {
    String token = tokenHeader.substring(8,tokenHeader.length()-1);
    String userName = jwTservice.getUserName(token);

    Optional<PropertyUser> optional = propertyUserRepository.findByUsername(userName);
if (optional.isPresent()){
    PropertyUser user = optional.get();

    // To keep track of current user logged in
    // these three line create sessons
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
            (user, null, Collections.singleton(new SimpleGrantedAuthority(user.getUserRole())) );
    authentication.setDetails(new WebAuthenticationDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
}


}
        filterChain.doFilter(request,response);

    }
}
