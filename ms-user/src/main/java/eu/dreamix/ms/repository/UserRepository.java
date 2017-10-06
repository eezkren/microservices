package eu.dreamix.ms.repository;

import eu.dreamix.ms.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

}