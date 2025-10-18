package com.OnlineCart.config;

import com.OnlineCart.model.UserDatas;
import com.OnlineCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceConfig implements UserDetailsService {
   @Autowired
   private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDatas user = userRepository.findByEmail(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }


        return new CustomUser(user);
    }
}
