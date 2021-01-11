package com.imooc.dataobject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

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
