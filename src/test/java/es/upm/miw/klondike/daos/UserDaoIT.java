package es.upm.miw.klondike.daos;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class UserDaoIT {

    @Autowired
    private UserDao OUTUserDao;

    private User user;
    private User user2;
    private User user3;
    private User user4;

    @BeforeEach
    void seedDb() {
        this.user = User.builder().dni("12345678").
                active(true).login("userForUserDaoTest").password("usuario").name("Nombre de Usuaro").
                surname("Apellidos del Usuario").email("usuario@klondike.es").build();

        this.OUTUserDao.save(user);
    }

    @Test
    void testfindByLogin() {
        User expectedUser = this.OUTUserDao.findByLogin(user.getLogin())
                .orElseThrow(() -> new NotFoundException("User:" + user.getLogin()));
        assertEquals(user.getName(), expectedUser.getName());
        assertEquals(user.getSurname(), expectedUser.getSurname());
        assertEquals(user.getDni(), expectedUser.getDni());
        assertEquals(user.getEmail(), expectedUser.getEmail());
        assertEquals(user.getLogin(), expectedUser.getLogin());
        assertEquals(user.getPassword(), expectedUser.getPassword());
    }

    @AfterEach
    void delete() {
        this.OUTUserDao.delete(user);
    }

}