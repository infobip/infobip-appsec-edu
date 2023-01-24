package com.infobip.secops.controller;

import com.infobip.secops.model.Person;
import com.infobip.secops.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    @PersistenceContext
    private EntityManager entityManager;
    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public ResponseEntity all() {
        ResponseEntity httpEntity;
        try {
            List body = entityManager.createNativeQuery("SELECT * FROM person", Person.class).getResultList();
            httpEntity = new ResponseEntity<>(body, HttpStatus.OK);
        } catch (NoResultException ex) {
            httpEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            httpEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return httpEntity;
    }

    @GetMapping(value = "/{u}")
    public ResponseEntity sql(@PathVariable("u") String username) {
        ResponseEntity httpEntity;
        try {
            // SELECT * FROM person WHERE username = '' UNION SELECT 1,2,3
            String sqlQuery = String.format("SELECT * FROM person WHERE username = '%s'", username);
            Person body = (Person) entityManager.createNativeQuery(sqlQuery, Person.class).getSingleResult();
            httpEntity = new ResponseEntity<>(body, HttpStatus.OK);
        } catch (NoResultException ex) {
            httpEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            httpEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return httpEntity;
    }

    @GetMapping(value = "/denylist/{u}")
    public ResponseEntity getByManufacturer(@PathVariable("u") String username) {
        ResponseEntity response;
        try {
            List<String> keywords = Arrays.asList(
                "UNION", "INSERT", "SHOW", "BACKUP", "ALTER", "TRUNCATE", "WITH", "CALL",
                "INTERSECT", "SELECT", "CREATE", "FROM", "DROP", "DELETE", "JOIN", ";", "\\", "\""
            );
            for(String keyword : keywords) {
                if(username.toUpperCase().contains(keyword)) {
                    throw new NoResultException();
                }
            }
            String query = String.format("SELECT * FROM person WHERE username = '%s'", username);
            Person body = (Person) entityManager.createNativeQuery(query, Person.class).getSingleResult();
            response = new ResponseEntity<>(body, HttpStatus.OK);
        } catch (NoResultException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping(value = "/safe/{u}")
    public ResponseEntity sqlSafe(@PathVariable("u") String username) {
        ResponseEntity httpEntity;
        try {
            // Person body = personRepository.findByUsername(username)
            // OR
            String sqlQuery = "SELECT * FROM person WHERE username = :u";
            Person body = (Person) entityManager.createNativeQuery(sqlQuery, Person.class)
                    .setParameter("u", username)
                    .getSingleResult();
            httpEntity = new ResponseEntity<>(body, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            httpEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            httpEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return httpEntity;
    }
}
