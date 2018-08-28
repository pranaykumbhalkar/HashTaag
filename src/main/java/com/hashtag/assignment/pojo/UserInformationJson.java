package com.hashtag.assignment.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created By Pranay on 8/28/2018
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInformationJson {
    private String firstName;
    private String lastName;
    private Long userId;
    private String phone;
    private String email;
}
