package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wwd
 * 用户信息表
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class UserInfo {

    @Id
    private String id;
    private BigDecimal height;
    private BigDecimal weight;
    private String phone;
    private String wechatAccount;
    private String hobby;
    private String selfIntroduction;
    private String lifePhotos;
    private String job;
    private String skill;

    private Integer isAdmin;
    private String openId;

    private String realName;
    private String idCard;
    private Integer isRealNameValid;


    private Integer isMarried;
    private Date birthDate;
    private Integer constellation;//星座
    private Integer education;
    private Integer isBuyCar;
    private Integer isBuyHouse;
    private String corporation;//工作单位


    private Date createTime;
    private Date updTime;

}
