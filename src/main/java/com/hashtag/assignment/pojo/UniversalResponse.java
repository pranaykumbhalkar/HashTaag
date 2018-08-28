package com.hashtag.assignment.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Created By Pranay on 8/27/2018
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniversalResponse<T> {

    private ResponseCodeJson status;
    private List list;
    private T object;
    private Integer reqId;
}
