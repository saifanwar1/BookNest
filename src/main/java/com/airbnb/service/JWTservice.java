package com.airbnb.service;


import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;


@Service
public class JWTservice {
    @Value("${jwt.algorithm.Key}")
    private String algorithmKey;
    @Value("${jwt.issuar}")
    private String issuar;
    @Value("${jwt.expiry.duration}")
    private String expiryTime;


    private Algorithm algorithm;

    private final static String USER_NAME = "username";

    @PostConstruct
    public void postConstruct() {
        algorithm=Algorithm.HMAC256(algorithmKey);


    }

    public String generateToken(PropertyUser propertyUser) {
       return JWT.create()
                .withClaim(USER_NAME,propertyUser.getUsername())
               //.withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuar)
                .sign(algorithm);


    }

    public String getUserName(String token){

        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuar).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }

}