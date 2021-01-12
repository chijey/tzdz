package com.imooc.controller;

import com.imooc.VO.ResultVO;
import com.imooc.dataobject.Message;
import com.imooc.dataobject.Person;
import com.imooc.service.MessageService;
import com.imooc.service.PersonService;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;
    @PostMapping("/create")
    public ResultVO create(@RequestBody Person person) {
        Person personResult = personService.save(person);
        return ResultVOUtil.success(personResult);
    }
}
