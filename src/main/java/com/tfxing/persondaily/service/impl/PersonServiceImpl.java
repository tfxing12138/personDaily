package com.tfxing.persondaily.service.impl;

import com.tfxing.persondaily.dao.FlexPersonMapper;
import com.tfxing.persondaily.dao.PersonMapper;
import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.service.PersonService;
import org.framework.core.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private FlexPersonMapper personMapper;

    @Override
    public List<Person> list() {
        List<Person> personList = personMapper.selectAll();
        return personList;
    }
}
