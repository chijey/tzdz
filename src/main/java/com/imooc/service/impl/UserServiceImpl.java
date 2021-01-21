package com.imooc.service.impl;

import com.imooc.VO.UserVO;
import com.imooc.dataobject.User;
import com.imooc.dataobject.UserInfo;
import com.imooc.param.PersonParam;
import com.imooc.repository.UserInfoRepository;
import com.imooc.repository.UserRepository;
import com.imooc.service.UserService;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by wwd
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public User findByOpenid(String openid) {
        return userRepository.findByOpenid(openid);
    }


    @Override
    public Page<UserInfo> pageination(PersonParam param, Pageable pageable) {
        Page<UserInfo> userInfosPage = userInfoRepository.findAll(pageable);
        return userInfosPage;
    }
}
