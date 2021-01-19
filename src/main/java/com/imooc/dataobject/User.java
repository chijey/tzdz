package com.imooc.dataobject;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Created by wwd
 * 用户信息表
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String phone;
    private String openid;
    private String zhuohao;//桌号
    private String renshu;//用餐人数
    private Integer sex;
    private String head;

    private String city;
    private String province;

    private Date createTime;
    private Date updateTime;
    private Date latestLoginTime;
}
