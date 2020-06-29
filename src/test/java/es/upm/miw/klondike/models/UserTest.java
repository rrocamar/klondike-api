package es.upm.miw.klondike.models;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserBuilder() {
        LocalDateTime registrationDate = LocalDateTime.now();
        Role[] roles = {Role.PLAYER};
        User user = User.builder().dni("12345678").
                active(true).login("usuario").password("usuario").name("Nombre de Usuaro").registrationDate(registrationDate).
                surname("Apellidos del Usuario").email("usuario@klondike.es").roles(roles).build();
        assertEquals("12345678", user.getDni());
        assertEquals(registrationDate, user.getRegistrationDate());
        assertEquals("usuario", user.getLogin());
        assertEquals("Nombre de Usuaro", user.getName());
        assertEquals("Apellidos del Usuario", user.getSurname());
        assertEquals(true, user.isActive());
        assertEquals("usuario@klondike.es", user.getEmail());
        assertEquals("12345678", user.getDni());
        assertEquals(roles, user.getRoles());
    }

}