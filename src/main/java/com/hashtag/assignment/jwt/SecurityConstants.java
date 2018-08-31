package com.hashtag.assignment.jwt;

/**
 * Created By Pranay on 8/31/2018
 */

public class SecurityConstants {
  public static final String SECRET = "ShantaBai";
  public static final long EXPIRATION_TIME = 10; // 10 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/signup";
  public static final String SIGN_IN_URL = "/user/login";


}