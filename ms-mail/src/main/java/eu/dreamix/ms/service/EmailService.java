package eu.dreamix.ms.service;

import eu.dreamix.ms.entity.dto.UserDto;

public interface EmailService {

    void sendSimpleMessage(UserDto input);
}
