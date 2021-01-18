package com.imooc.repository;

import com.imooc.dataobject.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByOpenid(String openid);
    User findById(Integer id);
}
