package com.hashtag.assignment.services;

import com.hashtag.assignment.models.Users;
import com.hashtag.assignment.pojo.ResponseCodeJson;
import com.hashtag.assignment.pojo.UniversalResponse;
import com.hashtag.assignment.pojo.UserPojo;
import com.hashtag.assignment.repository.UsersRepository;
import com.hashtag.assignment.utils.AtomicIdCounter;
import com.hashtag.assignment.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created By Pranay on 8/27/2018
 */

@Service
public class SignUpService {


    @Autowired
    private UsersRepository usersRepository;

    public UniversalResponse signUp(UserPojo req) {
        UniversalResponse response = new UniversalResponse();
        String email = StrUtil.nonNull(req.getEmail());
        Boolean isAlreadyExist = usersRepository.existsByEmail(email);
        if (isAlreadyExist) {
            response.setStatus(new ResponseCodeJson("Email already registered", 421));
            return response;
        }
        createNewUser(req);
        response.setStatus(new ResponseCodeJson("success", 200));
        return response;
    }

    private void createNewUser(UserPojo req) {
        Users user = new Users();
        user.setUserId(AtomicIdCounter.getRandomUID());
        user.setFirstName(StrUtil.nonNull(req.getFirstName()));
        user.setLastName(StrUtil.nonNull(req.getLastName()));
        user.setEmail(StrUtil.nonNull(req.getEmail()));
        user.setPassword(StrUtil.nonNull(req.getPassword()));
        user.setCreditAvailable(50);
        user.setDateOfJoining(new Date());
        user.setActive(1);
        user.setPhone(StrUtil.nonNull(req.getPhone()));
        user.setProfilePicture("");
        usersRepository.save(user);
    }
}
