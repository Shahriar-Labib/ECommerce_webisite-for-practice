package com.OnlineCart.repository;

import com.OnlineCart.model.UserDatas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDatas,Integer> {

    public UserDatas findByEmail(String email);
}
