package com.example.multitenancy.controller;

import com.example.multitenancy.entity.PersonEntity;
import com.example.multitenancy.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<PersonEntity> getPersons() {
        return personRepository.findAll();
    }

    @Transactional
    @PostMapping
    public PersonEntity savePerson(@RequestBody PersonEntity person) {
        LOGGER.info("Save person {}", person);
        return personRepository.save(person);
    }
}
