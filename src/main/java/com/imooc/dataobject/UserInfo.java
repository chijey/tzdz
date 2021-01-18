package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Date createTime;
    private Date updTime;
    private Integer isAdmin;

}
