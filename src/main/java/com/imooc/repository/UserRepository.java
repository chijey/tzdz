package com.imooc.repository;

import com.imooc.VO.UserVO;
import com.imooc.dataobject.User;

import com.imooc.param.PersonParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>  {
    User findByOpenid(String openid);
    User findById(Integer id);

//    List<UserVO> pageination(PersonParam param, Pageable pageable);
}
