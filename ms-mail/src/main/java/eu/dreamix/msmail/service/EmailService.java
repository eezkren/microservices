package eu.dreamix.msmail.service;

import eu.dreamix.msmail.entity.dto.UserDto;

public interface EmailService {

    void sendSimpleMessage(UserDto input);
}
