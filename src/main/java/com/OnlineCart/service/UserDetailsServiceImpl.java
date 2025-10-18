package com.OnlineCart.service;

import com.OnlineCart.model.UserDatas;
import com.OnlineCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

    @Override
    public UserDatas saveUser(UserDatas user) {
        user.setRole("ROLE_USER");
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
       UserDatas userDatas = userRepository.save(user);
        return userDatas;
    }

    @Override
    public UserDatas getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
