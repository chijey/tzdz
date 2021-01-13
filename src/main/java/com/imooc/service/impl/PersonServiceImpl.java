package com.imooc.service.impl;

import com.imooc.dataobject.Person;
import com.imooc.param.PersonParam;
import com.imooc.repository.PersonRepository;
import com.imooc.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.*;

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

    @Override
    public Page<Person> pagination(PersonParam param, Pageable pageable) {
        return null;
    }

//    @Override
//    public Page<Person> pagination(PersonParam param, Pageable pageable) {
//        List<Person> persons = new ArrayList<>();
//        long offset = pageable.getOffset();
//        int pageSize = pageable.getPageSize();
//        String sorts = pageable.getSort().toString();
//
//        //  条件
////        String where = getWhereCondition(param, RequestContext.getCompanyId());
////        //  排序
////        String orderBy = createSortBy(sorts);
////
////        //  总数查询
////        StringBuilder countSql = new StringBuilder("select count(d.id) as count from device d LEFT JOIN model m ON d.model_id = m.id LEFT JOIN device_group g ON d.group_id = g.id ").append(where);
////        long count = Optional.ofNullable(entityManager.createNativeQuery(countSql.toString()).getSingleResult()).map(Object::toString).map(Long::parseLong).orElse(0L);
////        if (0 == count) {
////            return new Page<>(devices, pageable.getPageNumber(), pageable.getPageSize(), count, pageable.getSort());
////        }
//            Integer count = 0;
////
////        //  分页查询
////        StringBuilder sql = new StringBuilder("select * from device d LEFT JOIN model m ON d.model_id = m.id LEFT JOIN device_group g ON d.group_id = g.id")
////                .append(where)
////                .append(orderBy)
////                .append(ConvertUtils.limit(offset, pageSize));
////        logger.info("[DeviceService]Paging query.sql: {}", sql.toString());
////        Query query = entityManager.createNativeQuery(sql.toString(), Device.class);
////        persons = query.getResultList();
//
//        return new Page<>(persons, pageable.getPageNumber(), pageable.getPageSize(), count, pageable.getSort());
//    }

}
