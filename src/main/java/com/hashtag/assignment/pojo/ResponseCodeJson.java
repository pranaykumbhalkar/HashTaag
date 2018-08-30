package com.hashtag.assignment.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created By Pranay on 8/27/2018
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCodeJson {

    /**
     * message : user for send custom error messages
     */
    private String message;

    /**
     * errorCode : user for send custom http error code
     */
    private Integer errorCode;

    public ResponseCodeJson() {

    }

    public ResponseCodeJson(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
