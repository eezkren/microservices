package eu.dreamix.msmail.controller;

import eu.dreamix.msmail.entity.Mail;
import eu.dreamix.msmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MailController {

    @Autowired
    public EmailService emailService;

    @RequestMapping(method = RequestMethod.POST, path = "/send")
    public ResponseEntity<?> register(@RequestBody Mail input) {

        emailService.sendSimpleMessage(input);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
