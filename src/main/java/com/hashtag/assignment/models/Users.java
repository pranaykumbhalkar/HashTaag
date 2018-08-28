package com.hashtag.assignment.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created By Pranay on 8/27/2018
 */

@Data
@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "DATEOFJOINING")
    private Date dateOfJoining;

    @Column(name = "PROFILEPICTURE")
    private String profilePicture;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PASSWORD")
    private String password;

    /**
     * 1 : Active User
     * 0 : Blocked
     */
    @Column(name = "ACTIVE")
    private Integer active;

    @Column(name = "CREDITAVAILABLE")
    private Integer creditAvailable;
}
