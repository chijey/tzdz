package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Message {
    @Id
    @GeneratedValue
    private String id;
    private String userId;
    private String objId;
    private String content;
    private Date createTime;
    private Date updTime;


}
