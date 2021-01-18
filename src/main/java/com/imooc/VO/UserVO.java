package com.imooc.VO;

import com.imooc.dataobject.UserInfo;
import com.imooc.form.VerifyForm;
import lombok.Data;

@Data
public class UserVO {
    private UserInfo userInfo;
    private String username;
    private String phone;
    private Integer sex;
    private String openid;
    private VerifyForm verifyForm;
}
