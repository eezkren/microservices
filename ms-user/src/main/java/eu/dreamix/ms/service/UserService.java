package eu.dreamix.ms.service;

import eu.dreamix.ms.entity.User;

public interface UserService {

    User registerUser(User input);

    Iterable<User> findAll();

    User findById(Long id);
}
