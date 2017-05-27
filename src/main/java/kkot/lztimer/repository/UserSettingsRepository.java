package kkot.lztimer.repository;

import kkot.lztimer.domain.User;
import kkot.lztimer.domain.UserSettings;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings,Long> {
    UserSettings findUserSettingsByUser(User user);
}
