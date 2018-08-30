package com.hashtag.assignment.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created By Pranay on 8/30/2018
 */
@Data
public class TransactionResponse {
    private ResponseCodeJson status;
    private List<TransactionJson> transactionList;
}
