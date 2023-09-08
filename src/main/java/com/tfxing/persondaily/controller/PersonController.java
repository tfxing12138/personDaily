package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    /*@Resource
    private PersonService personService;

    @PostMapping("/list")
    public List<Person> list() {
        return personService.list();
    }*/
}
