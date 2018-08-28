package com.hashtag.assignment.services;

import com.hashtag.assignment.models.Users;
import com.hashtag.assignment.pojo.ResponseCodeJson;
import com.hashtag.assignment.pojo.UniversalResponse;
import com.hashtag.assignment.pojo.UserPojo;
import com.hashtag.assignment.repository.UsersRepository;
import com.hashtag.assignment.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created By Pranay on 8/27/2018
 */

@Service
public class LoginService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Validate user as per email id and password
     *
     * @param req -> email
     * @param req -> password
     *            <p>
     *  Error Code:
     *            200 - success (Rend userId as response)
     *            421 - Email not registered
     *            422 - Password mismatch
     *            423 - Account blocked
     */
    public UniversalResponse login(UserPojo req) {
        UniversalResponse<Long> res = new UniversalResponse<>();
        String email = StrUtil.nonNull(req.getEmail());
        String password = StrUtil.nonNull(req.getPassword());
        Users user = usersRepository.findByEmail(email);
        ResponseCodeJson rc = validateUser(user, password);
        if (rc.getErrorCode() == 200)
            res.setObject(user.getUserId());
        res.setStatus(rc);
        return res;
    }

    private ResponseCodeJson validateUser(Users user, String password) {
        if (user == null)
            return new ResponseCodeJson("Email not registered", 421);
        if (!password.equalsIgnoreCase(user.getPassword()))
            return new ResponseCodeJson("Password mismatch", 422);
        if (user.getActive().equals(0))
            return new ResponseCodeJson("Account blocked", 423);
        return new ResponseCodeJson("success", 200);
    }
}
