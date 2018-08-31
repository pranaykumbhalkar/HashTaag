package com.hashtag.assignment.services;

import com.hashtag.assignment.jwt.JwtTokenGenerator;
import com.hashtag.assignment.models.Users;
import com.hashtag.assignment.pojo.*;
import com.hashtag.assignment.repository.UsersRepository;
import com.hashtag.assignment.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created By Pranay on 8/27/2018
 */

@Service
public class LoginService {

    //@Autowired
    private final UsersRepository usersRepository;
    //@Autowired
    private final JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    public LoginService(
            UsersRepository usersRepository,
            JwtTokenGenerator jwtTokenGenerator) {
        this.usersRepository = usersRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    /**
     * Validate user as per email id and password,
     * If request user are not valid user, user will get specific error message
     * If request user have valid credential, API return userId for further operation
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

    public LoginResponse login(LoginJson req) {
        LoginResponse res = new LoginResponse();
        String email = StrUtil.nonNull(req.getEmail());
        String password = StrUtil.nonNull(req.getPassword());
        Users user = usersRepository.findByEmail(email);
        ResponseCodeJson rc = validateUser(user, password);
        if (rc.getErrorCode() == 200) {
            res.setUserId(user.getUserId());
            res.setAuthToken("Bearer " + jwtTokenGenerator.generateToken(email));
        }
        res.setStatus(rc);
        return res;
    }

    private ResponseCodeJson validateUser(Users user, String password) {
        if (user == null)
            return new ResponseCodeJson("Email not registered", 421);
        if (!password.equals(user.getPassword()))
            return new ResponseCodeJson("Password mismatch", 422);
        if (user.getActive().equals(0))
            return new ResponseCodeJson("Account blocked", 423);
        return new ResponseCodeJson("success", 200);
    }

}
