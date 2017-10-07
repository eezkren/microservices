package eu.dreamix.msmail.repository;

import eu.dreamix.msmail.entity.Mail;
import org.springframework.data.repository.CrudRepository;


public interface MailRepository extends CrudRepository<Mail, Long> {

}