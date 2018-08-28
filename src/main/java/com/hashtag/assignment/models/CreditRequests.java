package com.hashtag.assignment.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created By Pranay on 8/28/2018
 */

@Data
@Entity
@Table(name = "CREDITREQUESTS", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class CreditRequests {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "REQUESTID")
    private Long requestId;

    @Column(name = "PAYERUSERID")
    private Long payerUserId;

    @Column(name = "EVENTDATE")
    private Date eventDate;

    @Column(name = "REQUESTEDAMOUNT")
    private Integer requestedAmount;

    @Column(name = "EXPIRYDATE")
    private Date expiryDate;

    /**
     * 1 - Active
     * 0 - InActive
     */

    @Column(name = "ACTIVE")
    private Integer active;

    /**
     * ACCEPTED,REJECTED,PENDING
     */

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COMMENT")
    private String comment;



}
