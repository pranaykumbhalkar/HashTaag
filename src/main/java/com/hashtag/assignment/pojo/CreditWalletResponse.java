package com.hashtag.assignment.pojo;

import lombok.Data;

/**
 * Created By Pranay on 8/30/2018
 */
@Data
public class CreditWalletResponse {
    private ResponseCodeJson status;
    private String obfuscatedTransactionId;
}
