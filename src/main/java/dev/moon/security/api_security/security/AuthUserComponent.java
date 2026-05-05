package dev.moon.security.api_security.security;

import dev.moon.security.api_security.dao.UserDao;
import dev.moon.security.api_security.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserComponent implements UserDetailsService {
  @Autowired
  private UserDao userDao;

  @Override
  public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userDao.getUserByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found: " + username);
    }

    return new AuthUser(
            user.getId(),
            user.getUsername(),
            user.getPassword()
    );
  }
}
