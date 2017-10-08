package eu.dreamix.msmail.service;

import eu.dreamix.msmail.entity.Mail;
import eu.dreamix.msmail.entity.dto.UserDto;

public interface EmailService {

    void sendSimpleMessage(Mail input);

    void sendSimpleMessage(UserDto input);
}
