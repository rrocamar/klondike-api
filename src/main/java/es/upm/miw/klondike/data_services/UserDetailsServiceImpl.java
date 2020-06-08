
package es.upm.miw.klondike.data_services;

import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Role;
import es.upm.miw.klondike.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Qualifier("klondike.users")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Login not found: " + login));
        return this.userBuilder(user.getLogin(), user.getPassword(), new Role[]{Role.PLAYER}, user.isActive());

    }

    private org.springframework.security.core.userdetails.User userBuilder(String login, String password, Role[] roles,
                                                                           boolean active) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.roleName()));
        }
        return new org.springframework.security.core.userdetails.User(login, password, active, true,
                true, true, authorities);
    }
}