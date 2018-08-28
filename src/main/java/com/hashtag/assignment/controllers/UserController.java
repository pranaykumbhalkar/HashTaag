package com.hashtag.assignment.controllers;


import com.hashtag.assignment.pojo.ResponseCodeJson;
import com.hashtag.assignment.pojo.UniversalResponse;
import com.hashtag.assignment.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created By Pranay on 8/28/2018
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/search/{userId}/{pageNo}/{searchString}", method = RequestMethod.GET)
    public ResponseEntity<UniversalResponse> searchUser(@PathVariable("userId") Long userId,
                                                        @PathVariable("pageNo") Integer pageNo,
                                                        @PathVariable("searchString") String searchString) throws Exception {
        logger.info("searchUser Request: " + pageNo + " " + searchString);
        UniversalResponse response = userService.searchUser(userId, searchString, pageNo);
        ResponseCodeJson rc = response.getStatus();
        return new ResponseEntity<>(response, HttpStatus.valueOf(rc.getErrorCode()));
    }
}
