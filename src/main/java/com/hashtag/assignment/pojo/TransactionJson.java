package com.hashtag.assignment.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * Created By Pranay on 8/28/2018
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionJson {
    private Long transactionId;
    private String obfuscatedTransactionId;
    private Date eventDate;
    private String transactionWith;
    private String type;
    private Integer transactionAmount;
    private String status;
    private String comment;
}
