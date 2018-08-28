package com.hashtag.assignment.repository;

import com.hashtag.assignment.models.CreditRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Pranay on 8/28/2018
 */

@Repository
public interface CreditRequestsRepository extends JpaRepository<CreditRequests, Long> {

    Boolean existsByUserIdAndActive(Long userId, Integer active);
    CreditRequests findByRequestId(Long requestId);
}
