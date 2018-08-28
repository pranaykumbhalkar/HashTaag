package com.hashtag.assignment.services;

import com.hashtag.assignment.constants.Constants;
import com.hashtag.assignment.models.CreditRequests;
import com.hashtag.assignment.models.Transactions;
import com.hashtag.assignment.models.Users;
import com.hashtag.assignment.pojo.MakeTransactionJson;
import com.hashtag.assignment.pojo.ResponseCodeJson;
import com.hashtag.assignment.pojo.TransactionJson;
import com.hashtag.assignment.pojo.UniversalResponse;
import com.hashtag.assignment.repository.CreditRequestsRepository;
import com.hashtag.assignment.repository.TransactionsRepository;
import com.hashtag.assignment.repository.UsersRepository;
import com.hashtag.assignment.utils.AtomicIdCounter;
import com.hashtag.assignment.utils.RandomString;
import com.hashtag.assignment.utils.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created By Pranay on 8/27/2018
 */

@Service
public class TransactionService implements Constants {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private CreditRequestsRepository creditRequestsRepository;


    public UniversalResponse creditUserWallet(MakeTransactionJson req) {
        UniversalResponse<String> response = new UniversalResponse<>();
        Integer currentMonthCredited = transactionsRepository.getCurrentMonthTransactionAmountByUserIdAndTransactionType(req.getUserId(), CREDIT);

        currentMonthCredited = currentMonthCredited == null ? req.getAmount() : currentMonthCredited + req.getAmount();
        if (currentMonthCredited >= 10000) {
            response.setStatus(new ResponseCodeJson("You reached monthly limit", 421));
            return response;
        }
        RandomString random = new RandomString(16);
        String transactionId = random.getRandomString();
        makeTransaction(req.getUserId(), 0L, transactionId, CREDIT, req.getAmount(), req.getComment());
        response.setStatus(new ResponseCodeJson("success", 200));
        response.setObject(transactionId);
        return response;
    }


    public UniversalResponse transferCreditToAnotherUser(MakeTransactionJson req) {
        UniversalResponse<String> response = new UniversalResponse<>();
        Users user = usersRepository.findByUserId(req.getUserId());
        if (user == null || user.getCreditAvailable() < req.getAmount()) {
            response.setStatus(new ResponseCodeJson("Credit not available to transfer", 421));
            return response;
        }
        RandomString random = new RandomString(16);
        String transactionId = random.getRandomString();

        //Transaction record for debit transferred amount
        makeTransaction(req.getUserId(), req.getTransactionWith(), transactionId, DEBIT, req.getAmount(), req.getComment());
        //Transaction record for credit transferred amount
        makeTransaction(req.getTransactionWith(), req.getUserId(), transactionId, CREDIT, req.getAmount(), req.getComment());

        response.setObject(transactionId);
        response.setStatus(new ResponseCodeJson("success", 200));
        return response;
    }

    public UniversalResponse getAllTransactionByUserId(Long userId, Integer pageNo, Integer limit) {
        UniversalResponse response = new UniversalResponse();
        List<Transactions> transactionsList = transactionsRepository.findByUserIdOrderByIdDesc(userId, new PageRequest(pageNo - 1, limit));
        if (transactionsList.size() == 0) {
            response.setStatus(new ResponseCodeJson("User not did any transaction yet", 421));
            return response;
        }
        List<TransactionJson> transactionJsonList = new ArrayList<>();
        TransactionJson transactionJson;
        for (Transactions transactions : transactionsList) {
            transactionJson = new TransactionJson();
            BeanUtils.copyProperties(transactions, transactionJson);
            transactionJson.setTransactionWith(getTransactionPartnerName(transactions.getTransactionWith()));
            transactionJsonList.add(transactionJson);
        }
        response.setList(transactionJsonList);
        response.setStatus(new ResponseCodeJson("success", 200));
        return response;
    }


    /**
     * This method only use to save credit request
     * We need to notify user after getting credit request
     * Then user can accept or reject request
     */

    public UniversalResponse requestForCredit(MakeTransactionJson req) {
        UniversalResponse<Long> response = new UniversalResponse<>();
        Boolean isLiveRequest = creditRequestsRepository.existsByUserIdAndActive(req.getUserId(), 1);
        if (isLiveRequest) {
            response.setStatus(new ResponseCodeJson("You already have active credit request", 421));
            return response;
        }
        Long requestId = saveCreditRequest(req);
        response.setStatus(new ResponseCodeJson("success", 200));
        response.setObject(requestId);
        return response;
    }

    public UniversalResponse acceptCreditRequest(MakeTransactionJson req) {
        UniversalResponse response = new UniversalResponse();
        CreditRequests creditRequests = creditRequestsRepository.findByRequestId(req.getCreditRequestId());
        if (creditRequests.getActive().equals(0)) {
            response.setStatus(new ResponseCodeJson("InActive credit request", 421));
            return response;
        }
        req = getCompatibleObj(creditRequests, req);
        response = transferCreditToAnotherUser(req);
        if (response.getStatus().getErrorCode() == 200) {
            creditRequests.setStatus(ACCEPTED);
            creditRequests.setActive(0);
            creditRequestsRepository.save(creditRequests);
        }
        return response;
    }


    public UniversalResponse rejectCreditRequest(MakeTransactionJson req) {
        UniversalResponse response = new UniversalResponse();
        CreditRequests creditRequests = creditRequestsRepository.findByRequestId(req.getCreditRequestId());
        if (creditRequests.getActive().equals(0)) {
            response.setStatus(new ResponseCodeJson("InActive credit request", 421));
            return response;
        }
        creditRequests.setActive(0);
        creditRequests.setStatus(REJECTED);
        creditRequestsRepository.save(creditRequests);
        response.setStatus(new ResponseCodeJson("success", 200));
        return response;
    }


    private Long saveCreditRequest(MakeTransactionJson req) {
        Date currentDate = new Date();
        CreditRequests creditRequests = new CreditRequests();
        creditRequests.setUserId(req.getUserId());
        creditRequests.setPayerUserId(req.getTransactionWith());
        creditRequests.setRequestedAmount(req.getAmount());
        creditRequests.setActive(1);
        creditRequests.setEventDate(currentDate);
        creditRequests.setRequestId(AtomicIdCounter.getRandomUID());
        creditRequests.setStatus(PENDING);
        creditRequests.setExpiryDate(new Date(currentDate.getTime() + 864000000)); //automatically invalid after 10 day's
        creditRequestsRepository.save(creditRequests);
        return creditRequests.getRequestId();
    }


    private void makeTransaction(Long userId, Long partnerUserId, String transactionId, String type, Integer amount, String comment) {
        Transactions transaction = new Transactions();
        transaction.setUserId(userId);
        transaction.setEventDate(new Date());
        transaction.setTransactionId(AtomicIdCounter.getRandomUID());
        transaction.setObfuscatedTransactionId(transactionId);
        transaction.setStatus(SUCCESS);
        transaction.setTransactionWith(partnerUserId);
        transaction.setType(type);
        transaction.setTransactionAmount(amount);
        transaction.setComment(StrUtil.nonNull(comment));
        transactionsRepository.save(transaction);

        Users users = usersRepository.findByUserId(userId);
        if (CREDIT.equalsIgnoreCase(type))
            users.setCreditAvailable(users.getCreditAvailable() + amount);
        else
            users.setCreditAvailable(users.getCreditAvailable() - amount);
        usersRepository.save(users);
    }

    private String getTransactionPartnerName(Long userId) {
        if (userId != null && !userId.equals(0L)) {
            Users user = usersRepository.findByUserId(userId);
            return user.getFirstName();
        } else {
            return "";
        }
    }

    private MakeTransactionJson getCompatibleObj(CreditRequests creditRequests, MakeTransactionJson req) {
        req.setAmount(creditRequests.getRequestedAmount());
        req.setTransactionWith(creditRequests.getUserId());
        req.setComment(creditRequests.getComment());
        req.setUserId(creditRequests.getPayerUserId());
        return req;
    }


}
