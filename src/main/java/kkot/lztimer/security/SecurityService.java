package kkot.lztimer.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Currently it is wrapper for {@link SecurityUtils} provided by JHipster.
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
@Service
public class SecurityService {

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public String getCurrentUserLogin() {
        return SecurityUtils.getCurrentUserLogin();
    }
}
