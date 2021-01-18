package com.imooc.form;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
@Data
public class UserForm {


    @NotEmpty(message = "姓名必填")
    private String username;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "性别必填")
    private Integer sex;

    private String openid;

}
