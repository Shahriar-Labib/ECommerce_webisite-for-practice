package com.OnlineCart.service;

import com.OnlineCart.Utils.AppConstant;
import com.OnlineCart.model.UserDatas;
import com.OnlineCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

    @Override
    public UserDatas saveUser(UserDatas user) {
        user.setRole("ROLE_USER");
        user.setIsEnable(true);
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
       UserDatas userDatas = userRepository.save(user);
        return userDatas;
    }

    @Override
    public UserDatas getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDatas> getUsers(String role) {
       return userRepository.findByRole(role);

    }

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {

        Optional<UserDatas> findByUsers = userRepository.findById(id);

        if(findByUsers.isPresent())
        {
            UserDatas userDatas = findByUsers.get();
            userDatas.setIsEnable(status);
            userRepository.save(userDatas);
            return true;
        }
        return false;
    }

    @Override
    public void increaseFailedAttempt(UserDatas user) {
       int attempt = user.getFailedAttempt() + 1;
       user.setFailedAttempt(attempt);
       userRepository.save(user);
    }

    @Override
    public void userAccountLock(UserDatas user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }

    @Override
    public Boolean unlockAccountTimeExpired(UserDatas user) {
       long locktime = user.getLockTime().getTime();
       long unlockTime = locktime + AppConstant.UNLOCK_DURATION_TIME;

       long currentTime = System.currentTimeMillis();

       if(unlockTime < currentTime)
       {
           user.setAccountNonLocked(true);
           user.setFailedAttempt(0);
           user.setLockTime(null);
           userRepository.save(user);
           return true;
       }

        return false;
    }

    @Override
    public void resetAttempt(int userId) {


    }

    @Override
    public void updateUserRestToken(String email, String resetToken) {
       UserDatas userByEmail = userRepository.findByEmail(email);
       userByEmail.setReset_token(resetToken);
       userRepository.save(userByEmail);

    }
}
