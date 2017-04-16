package kkot.lztimer.repository;

import kkot.lztimer.domain.User;
import kkot.lztimer.domain.UserSettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserSettings entity.
 */
@SuppressWarnings("unused")
public interface UserSettingsRepository extends JpaRepository<UserSettings,Long> {
    UserSettings findUserSettingsByUser(User user);
}
