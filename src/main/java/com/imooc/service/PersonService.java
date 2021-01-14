package com.imooc.service;


import com.imooc.dataobject.Person;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.OrderDTO;
import com.imooc.param.PersonParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    Person save(Person person);

    Page<Person> pagination(PersonParam param, Pageable pageable);
    Page<Person> findAll(Pageable pageable);
}
