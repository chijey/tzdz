package com.imooc.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserInfoForm {
    private Integer userId;
    private String name;
    private String nickName;
    private BigDecimal height;
    private BigDecimal weight;
    private String phone;
    private String wechatAccount;
    private String avatar;
    private String hobby;
    private String selfIntroduction;
    private String lifePhotos;
    private String job;
    private String skill;
    private Integer isAdmin;

}
