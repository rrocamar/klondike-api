package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.business_services.JwtService;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.TokenDto;
import es.upm.miw.klondike.dtos.UserDto;
import es.upm.miw.klondike.dtos.UserMinimunDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.exceptions.ForbiddenException;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Role;
import es.upm.miw.klondike.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtService jwtService;

    public TokenDto login(String login) {
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User:" + login));
        String[] roles = Arrays.stream(user.getRoles()).map(Role::name).toArray(String[]::new);
        return new TokenDto(jwtService.createToken(user.getLogin(), user.getName() + " " + user.getSurname(), roles));
    }

    public UserDto readUser(String login, String claimLogin, List<String> claimRoles) {
        User user = this.userDao.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User:" + login));
        this.authorized(claimLogin, claimRoles, login, Arrays.stream(user.getRoles())
                .map(Role::roleName).collect(Collectors.toList()));
        return new UserDto(user);
    }

    private void authorized(String claimUsername, List<String> claimRoles, String username, List<String> userRoles) {
        if (claimRoles.contains(Role.PLAYER.roleName()) || claimUsername.equals(username)) {
            return;
        }
        throw new ForbiddenException("User (" + username + ")");
    }

    public UserDto create(UserDto userDto) {
        if(this.userDao.findByLogin(userDto.getLogin()).isPresent()) {
            throw new BadRequestException("User login (" + userDto.getLogin() + ") already exist.");
        }
        User saved = User.builder().login(userDto.getLogin()).name(userDto.getName())
                .surname(userDto.getSurname()).email(userDto.getEmail()).dni(userDto.getDni())
                .active(userDto.isActive()).roles(new Role[]{Role.PLAYER}).password(userDto.getPassword())
                .registrationDate(userDto.getRegistrationDate()).build();
        this.userDao.save(saved);
        return new UserDto(saved);
    }

    public UserMinimunDto isAvailable(String login) {
        UserMinimunDto userMinimunDto = new UserMinimunDto();
        userMinimunDto.setLogin(login);
        Optional<User> optionalUser = this.userDao.findByLogin(login);
        userMinimunDto.setAvailable(!optionalUser.isPresent());
        return userMinimunDto;
    }
}