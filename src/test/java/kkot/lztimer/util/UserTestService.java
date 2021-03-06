package kkot.lztimer.util;

import kkot.lztimer.domain.User;
import kkot.lztimer.repository.UserRepository;
import kkot.lztimer.service.UserService;
import kkot.lztimer.web.rest.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates users for tests.
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
@Service
public class UserTestService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates and saves new user with login given as argument.
     * @param login user login
     * @return new user
     */
    public User createUser(String login) {
        return userService.createUser(login, "password_" + login, "John", "Doe",
            login + "@localhost", "http://placehold.it/50x50", "en-US", true);
    }
}
