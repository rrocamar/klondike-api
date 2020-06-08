package es.upm.miw.klondike.daos;

import es.upm.miw.klondike.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao  extends JpaRepository<User, Long>{

    Optional<User> findByLogin(String login);

}
