package com.hashtag.assignment.controllers;

import com.hashtag.assignment.pojo.*;
import com.hashtag.assignment.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By Pranay on 8/27/2018
 */
@RestController
@RequestMapping("/signin")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginJson req) throws Exception {
        logger.info("Login Request: " + req);
        LoginResponse response = loginService.login(req);
        ResponseCodeJson rc = response.getStatus();
        return new ResponseEntity<>(response, HttpStatus.valueOf(rc.getErrorCode()));
    }

}
