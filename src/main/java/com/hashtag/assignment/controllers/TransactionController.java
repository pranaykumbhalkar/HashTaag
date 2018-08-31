package com.hashtag.assignment.controllers;

import com.hashtag.assignment.pojo.*;
import com.hashtag.assignment.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Created By Pranay on 8/27/2018
 */

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final static Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/{userId}/{pageNo}/{limit}", method = RequestMethod.GET)
    public ResponseEntity<TransactionResponse> getAllTransactionByUserId(@PathVariable("userId") Long userId,
                                                                         @PathVariable("pageNo") Integer pageNo,
                                                                         @PathVariable("limit") Integer limit,
                                                                         @RequestHeader("Authorization") String key) throws Exception {
        logger.info("Get all transaction by userId request: " + userId);
        TransactionResponse response = transactionService.getAllTransactionByUserId(userId, pageNo, limit);
        ResponseCodeJson rc = response.getStatus();
        return new ResponseEntity<>(response, HttpStatus.valueOf(rc.getErrorCode()));
    }

    @RequestMapping(value = "/credit", method = RequestMethod.POST)
    public ResponseEntity<CreditWalletResponse> creditUserWallet(@RequestBody CreditWalletJson req,
                                                                 @RequestHeader("Authorization") String key) throws Exception {
        logger.info("Credit user wallet request: " + req);
        Assert.isTrue(req.getAmount() <= 1000, "Max 1000 credits allows per transaction");
        CreditWalletResponse response = transactionService.creditUserWallet(req);
        ResponseCodeJson rc = response.getStatus();
        return new ResponseEntity<>(response, HttpStatus.valueOf(rc.getErrorCode()));
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity<CreditWalletResponse> transferCreditToAnotherUser(@RequestBody MakeTransactionJson req,
                                                                            @RequestHeader("Authorization") String key) throws Exception {
        logger.info("Transfer credit request: " + req);
        //Assert.isTrue(req.getAmount() <= 1000,"Max 1000 credits allows per transaction");
        CreditWalletResponse response = transactionService.transferCreditToAnotherUser(req);
        ResponseCodeJson rc = response.getStatus();
        return new ResponseEntity<>(response, HttpStatus.valueOf(rc.getErrorCode()));
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResponseEntity<CreditRequestResponse> requestForCredit(@RequestBody MakeTransactionJson req,
                                                                  @RequestHeader("Authorization") String key) throws Exception {
        logger.info("Request for credit request: " + req);
        CreditRequestResponse response = transactionService.requestForCredit(req);
        ResponseCodeJson rc = response.getStatus();
        return new ResponseEntity<>(response, HttpStatus.valueOf(rc.getErrorCode()));
    }

    @RequestMapping(value = "/request/accept", method = RequestMethod.POST)
    public ResponseEntity<CreditWalletResponse> acceptCreditRequest(@RequestBody ActionOnCreditRequestJson req,
                                                                    @RequestHeader("Authorization") String key) throws Exception {
        logger.info("Accept credit request: " + req);
        CreditWalletResponse response = transactionService.acceptCreditRequest(req);
        ResponseCodeJson rc = response.getStatus();
        return new ResponseEntity<>(response, HttpStatus.valueOf(rc.getErrorCode()));
    }

    @RequestMapping(value = "/request/reject", method = RequestMethod.POST)
    public ResponseEntity<ResponseCodeJson> rejectCreditRequest(@RequestBody ActionOnCreditRequestJson req,
                                                                @RequestHeader("Authorization") String key) throws Exception {
        logger.info("Accept credit request: " + req);
        ResponseCodeJson response = transactionService.rejectCreditRequest(req);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getErrorCode()));
    }
}
