package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.LoginController;
import es.upm.miw.klondike.dtos.TokenDto;
import es.upm.miw.klondike.dtos.UserDto;
import es.upm.miw.klondike.dtos.UserMinimunDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(LoginResource.AUTHENTICATION)
public class LoginResource {

    public static final String AUTHENTICATION = "/authorize";
    public static final String VALIDATE_TOKEN = "/validate";
    public static final String LOGIN = "/login";
    public static final String REGISTER_USER = "/register";
    public static final String LOGIN_AVAILABLE = "/available/{login}";

    @Autowired
    private LoginController loginController;

    @PreAuthorize("authenticated")
    @PostMapping(value = VALIDATE_TOKEN)
    @ResponseStatus(value = HttpStatus.OK)
    public void token(@AuthenticationPrincipal User activeUser) {
    }

    @PreAuthorize("authenticated")
    @PostMapping(value = LOGIN)
    public TokenDto login(@AuthenticationPrincipal User activeUser) {
        return loginController.login(activeUser.getUsername());
    }

    @PostMapping(value = REGISTER_USER)
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@Valid @RequestBody UserDto userDto) {
        this.loginController.create(userDto);
    }

    @GetMapping(value = LOGIN_AVAILABLE)
    public UserMinimunDto available(@PathVariable String login) {
        return this.loginController.isAvailable(login);
    }

}
