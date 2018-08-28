package com.hashtag.assignment.repository;

import com.hashtag.assignment.models.Transactions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created By Pranay on 8/27/2018
 */

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Query(value = "SELECT SUM(TRANSACTIONAMOUNT) FROM TRANSACTIONS WHERE USERID = ?1 AND TYPE = ?2 AND MONTH(EVENTDATE) = MONTH(CURRENT_DATE()) AND YEAR(EVENTDATE) = YEAR(CURRENT_DATE())", nativeQuery = true)
    Integer getCurrentMonthTransactionAmountByUserIdAndTransactionType(Long userId, String type);

    List<Transactions> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
}
