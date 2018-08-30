package com.hashtag.assignment.services;

import com.hashtag.assignment.models.Users;
import com.hashtag.assignment.pojo.ResponseCodeJson;
import com.hashtag.assignment.pojo.SearchUserResponse;
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

    /**
     * Search user for sending credit or to send credit request
     * If response user detail list, from user can select any user and we will store selected userId for further operation
     * We skip (not passing) current login user detail, because user can't able to send credit or credit request to own
     *
     * @param userId login user userId to skip login user details
     * @param searchString text which want to search (It should be firstName, email or phone)
     * @param pageNo for the pagination starting from 1
     *            <p>
     *  Error Code:
     *            200 - success (User details list)
     *            421 - No user found for specific search string
     */

    public SearchUserResponse searchUser(Long userId, String searchString, Integer pageNo) {
        SearchUserResponse response = new SearchUserResponse();
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
        response.setUserList(userInfoList);
        return response;
    }
}
