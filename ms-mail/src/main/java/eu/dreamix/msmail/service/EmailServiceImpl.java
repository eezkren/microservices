package eu.dreamix.msmail.service;


import eu.dreamix.msmail.entity.Mail;
import eu.dreamix.msmail.entity.dto.UserDto;
import eu.dreamix.msmail.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public MailRepository mailRepository;

    @Override
    public void sendSimpleMessage(Mail input) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(input.getTo());
            message.setSubject(input.getSubject());
            message.setText(input.getText());

            mailRepository.save(input);
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void sendSimpleMessage(UserDto input) {
        try {

            Mail newMail = new Mail();
            newMail.setTo(input.getUsername());
            newMail.setSubject("TestSubject");
            newMail.setText("TestText");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(newMail.getTo());
            message.setSubject(newMail.getSubject());
            message.setText(newMail.getText());

            mailRepository.save(newMail);
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }

    }
}
