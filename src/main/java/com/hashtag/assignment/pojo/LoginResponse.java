package com.hashtag.assignment.pojo;

import lombok.Data;

/**
 * Created By Pranay on 8/30/2018
 */
@Data
public class LoginResponse {
    private ResponseCodeJson status;
    private Long userId;
}
