package eu.dreamix.ms.controller;

import eu.dreamix.ms.entity.User;
import eu.dreamix.ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/members")
    public ResponseEntity<?> getAll() {

        Iterable<User> all = userRepository.findAll();

        return new ResponseEntity<Object>(all, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<?> register(@RequestBody User input) {

       User result = userRepository.save(input);

        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
