package eu.dreamix.ms.repository;

import eu.dreamix.ms.entity.Account;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AccountRepository extends Repository<Account, Long> {
    Optional<Account> findByUsername(String username);

    Account save(Account account);

}
