package com.imooc.service.impl;

import com.imooc.dataobject.Message;
import com.imooc.dataobject.Person;
import com.imooc.repository.PersonRepository;
import com.imooc.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    public Person save(Person person){
        String uuid = UUID.randomUUID().toString();
        person.setId(uuid);
        Date time = new Date();
        person.setCreateTime(time);
        person.setUpdTime(time);
        Person personResult = personRepository.save(person);
        return personResult;
    }

}
