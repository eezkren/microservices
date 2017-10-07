package eu.dreamix.msmail.service;

import eu.dreamix.msmail.entity.Mail;

public interface EmailService {

    void sendSimpleMessage(Mail input);

}
