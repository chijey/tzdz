package com.imooc.service.impl;

import com.imooc.dataobject.Message;
import com.imooc.repository.MessageRepository;
import com.imooc.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wwd
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    public Message save(Message message){
        String uuid = UUID.randomUUID().toString();
        message.setId(uuid);
        Date time = new Date();
        message.setCreateTime(time);
        message.setUpdTime(time);
        Message messageResult = messageRepository.save(message);
        return messageResult;
    }

}
