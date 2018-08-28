package com.hashtag.assignment.services;

import com.hashtag.assignment.models.Users;
import com.hashtag.assignment.pojo.ResponseCodeJson;
import com.hashtag.assignment.pojo.UniversalResponse;
import com.hashtag.assignment.pojo.UserInformationJson;
import com.hashtag.assignment.repository.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Pranay on 8/28/2018
 */

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public UniversalResponse searchUser(Long userId, String searchString, Integer pageNo) {
        UniversalResponse response = new UniversalResponse();
        List<Users> usersList = usersRepository.searchUser(userId, searchString, new PageRequest(pageNo - 1, 10));
        if (usersList.size() == 0) {
            response.setStatus(new ResponseCodeJson("User not found", 421));
            return response;
        }
        List<UserInformationJson> userInfoList = new ArrayList<>();
        UserInformationJson userInfo;
        for (Users user : usersList) {
            userInfo = new UserInformationJson();
            BeanUtils.copyProperties(user, userInfo);
            userInfoList.add(userInfo);
        }
        response.setStatus(new ResponseCodeJson("success", 200));
        response.setList(userInfoList);
        return response;
    }
}
