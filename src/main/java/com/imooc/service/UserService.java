package com.imooc.service;

import com.imooc.VO.UserVO;
import com.imooc.dataobject.User;
import com.imooc.dataobject.UserInfo;
import com.imooc.param.PersonParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户端
 * Created by wwd
 */
public interface UserService {

    /**
     * 通过openid查询用户信息
     *
     * @param openid
     * @return
     */
    User findByOpenid(String openid);

    Page<UserInfo> pageination(PersonParam param, Pageable pageable);
}
