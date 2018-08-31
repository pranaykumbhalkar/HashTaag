package com.hashtag.assignment.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.hashtag.assignment.jwt.SecurityConstants.EXPIRATION_TIME;

/**
 * Created By Pranay on 8/31/2018
 */

@Component
public class JwtTokenGenerator {

  public String generateToken(String uniqueId) {

    return Jwts.builder()
        .setSubject(uniqueId)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
        .compact();

  }
}