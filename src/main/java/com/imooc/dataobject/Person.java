package com.imooc.dataobject;

import com.imooc.VO.PersonVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Person {
    @Id
    private String id;
    private String name;
    private String nickName;
    private Integer sex;
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

    public PersonVO toVO() {

        return null;
    }

}
