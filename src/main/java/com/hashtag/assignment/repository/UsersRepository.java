package com.hashtag.assignment.repository;

import com.hashtag.assignment.models.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Pranay on 8/27/2018
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

    Boolean existsByEmail(String email);

    Users findByUserId(Long userId);

    @Query(value = "SELECT * FROM USERS WHERE USERID <> ?1 AND (FIRSTNAME LIKE %?2% OR PHONE LIKE %?2% OR EMAIL LIKE %?2%)" +
            " \n-- #pageable\n", nativeQuery = true)
    List<Users> searchUser(Long userId, String searchStr, Pageable pageable);
}
