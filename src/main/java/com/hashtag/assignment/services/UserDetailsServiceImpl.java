package com.hashtag.assignment.services;

import com.hashtag.assignment.models.Users;
import com.hashtag.assignment.pojo.LoginResponse;
import com.hashtag.assignment.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Created By Pranay on 8/31/2018
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UsersRepository userRepository;

  public UserDetailsServiceImpl(UsersRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Users user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new org.springframework.security.core.userdetails.User(email, user.getPassword(), emptyList());
  }


}
