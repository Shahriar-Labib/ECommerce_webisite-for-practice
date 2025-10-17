package com.OnlineCart.service;

import com.OnlineCart.model.UserDetails;
import com.OnlineCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

  @Autowired
  private UserRepository userRepository;

    @Override
    public UserDetails saveUser(UserDetails user) {

       UserDetails userDetails = userRepository.save(user);
        return userDetails;
    }
}
