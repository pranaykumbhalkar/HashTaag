package com.hashtag.assignment.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created By Pranay on 8/27/2018
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MakeTransactionJson {
    private Long userId;
    private Long transactionWith;
    private Integer amount;
    private String comment;
    private Long creditRequestId;
    private Integer reqId;
}
