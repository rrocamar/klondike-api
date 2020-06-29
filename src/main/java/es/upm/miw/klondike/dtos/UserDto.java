package es.upm.miw.klondike.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.upm.miw.klondike.models.Role;
import es.upm.miw.klondike.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotNull(message = "Please provide a login")
    private String login;

    @NotNull(message = "Please provide a password")
    private String password;

    @Pattern(regexp = es.upm.miw.klondike.dtos.validations.Pattern.EMAIL_PATTERN)
    private String email;

    @Pattern(regexp = es.upm.miw.klondike.dtos.validations.Pattern.DNI_PATTERN)
    private String dni;

    @NotNull(message = "Please provide a name")
    private String name;

    @NotNull(message = "Please provide a surname")
    private String surname;

    private Boolean active;

    private String[] roles;

    private LocalDateTime registrationDate;

    public UserDto() {
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public UserDto(User user) {
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.dni = user.getDni();
        this.active = user.isActive();
        this.roles = Arrays.stream(user.getRoles()).map(Role::name).toArray(String[]::new);
        this.registrationDate = user.getRegistrationDate();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return this.roles;
    }
}