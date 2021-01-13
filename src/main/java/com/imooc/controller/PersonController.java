package com.imooc.controller;

import com.imooc.VO.PersonVO;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.Message;
import com.imooc.dataobject.Person;
import com.imooc.dto.OrderDTO;
import com.imooc.param.PersonParam;
import com.imooc.service.MessageService;
import com.imooc.service.PersonService;
import com.imooc.utils.ConvertUtils;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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


    @PostMapping("/pagination")
    public ResultVO pagination(@RequestBody PersonParam param, @RequestParam("page") Integer page, @RequestParam("size") Integer size,
                               @RequestParam("sort") String sorts) {
        Pageable pageable = ConvertUtils.pagingConvert(page,size,sorts);
        Page<Person> persons = personService.pagination(param, pageable);
        Page<PersonVO> deviceDtos = persons.map(Person::toVO);
        return ResultVOUtil.success(deviceDtos);
    }



}
