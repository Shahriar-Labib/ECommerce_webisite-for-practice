package com.OnlineCart.config;

import com.OnlineCart.Utils.AppConstant;
import com.OnlineCart.model.UserDatas;
import com.OnlineCart.repository.UserRepository;
import com.OnlineCart.service.UserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFaliureImpl extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

       String email = request.getParameter("username");

        UserDatas userDatas = userRepository.findByEmail(email);

        if(userDatas.getIsEnable())
        {
            if(userDatas.getAccountNonLocked())
            {
                if(userDatas.getFailedAttempt() < AppConstant.ATTEMPT_TIME)
                {
                    userService.increaseFailedAttempt(userDatas);
                }
                else{
                    userService.userAccountLock(userDatas);
                    exception = new LockedException("your account is locked !! failed attempt 3");
                }
            }
            else{
                if(userService.unlockAccountTimeExpired(userDatas))
                {
                    exception = new LockedException("Your account is unlocked !! Try to login");
                }

                else exception = new LockedException("your account is locked !! Try after sometimes" );
            }

        }
        else{
            exception = new LockedException("your account is inactive");
        }
        super.setDefaultFailureUrl("/signin?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
