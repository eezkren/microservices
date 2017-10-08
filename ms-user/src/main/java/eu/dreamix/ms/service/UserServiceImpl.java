package eu.dreamix.ms.service;

import eu.dreamix.ms.entity.User;
import eu.dreamix.ms.kafka.producer.Sender;
import eu.dreamix.ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private static String HELLOWORLD_TOPIC = "USER_CREATED_TOPIC";
    private UserRepository userRepository;
    private Sender sender;

    @Autowired
    UserServiceImpl(UserRepository userRepository, Sender sender) {
        this.userRepository = userRepository;
        this.sender = sender;
    }

    @Override
    public User registerUser(User input) {

        User createdUser = userRepository.save(input);
        sender.send(HELLOWORLD_TOPIC, createdUser);
        return createdUser;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
