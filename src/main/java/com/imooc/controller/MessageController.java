package com.imooc.controller;

import com.imooc.VO.ResultVO;
import com.imooc.dataobject.Message;
import com.imooc.service.MessageService;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @PostMapping("/create")
    public ResultVO create(@RequestBody Message message) {
        Message messageResut = messageService.save(message);
        return ResultVOUtil.success(messageResut);
    }
}
