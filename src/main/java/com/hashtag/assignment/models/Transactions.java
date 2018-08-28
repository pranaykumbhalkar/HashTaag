package com.hashtag.assignment.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created By Pranay on 8/27/2018
 */

@Data
@Entity
@Table(name = "TRANSACTIONS", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class Transactions {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERID")
    private Long userId;

    /**
     * transactionId is for internal use only
     */
    @Column(name = "TRANSACTIONID")
    private Long transactionId;

    /**
     * obfuscatedTransactionId is visible to user and user can use this id as reference
     */
    @Column(name = "OBFUSCATEDTRANSACTIONID")
    private String obfuscatedTransactionId;

    @Column(name = "EVENTDATE")
    private Date eventDate;

    /**
     * Transaction partner userId
     */
    @Column(name = "TRANSACTIONWITH")
    private Long transactionWith;

    /**
     * credit or debit
     */
    @Column(name = "TYPE")
    private String type;

    @Column(name = "TRANSACTIONAMOUNT")
    private Integer transactionAmount;

    /**
     * success, failed, in-process
     */
    @Column(name = "STATUS")
    private String status;

    @Column(name = "COMMENT")
    private String comment;

}
