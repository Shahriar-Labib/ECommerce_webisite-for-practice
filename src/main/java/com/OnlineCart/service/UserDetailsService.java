package com.OnlineCart.service;

import com.OnlineCart.model.UserDatas;

public interface UserDetailsService {

    public UserDatas saveUser(UserDatas user);

    public UserDatas getUserByEmail(String email);
}
