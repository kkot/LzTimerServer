package kkot.lztimer.service;

import kkot.lztimer.config.ApplicationProperties;
import kkot.lztimer.domain.User;
import kkot.lztimer.domain.UserSettings;
import kkot.lztimer.repository.UserSettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing UserSettings.
 */
@Service
@Transactional
public class UserSettingsService {
    private final Logger log = LoggerFactory.getLogger(UserSettingsService.class);

    private final UserSettingsRepository userSettingsRepository;
    private ApplicationProperties applicationProperties;

    public UserSettingsService(UserSettingsRepository userSettingsRepository,
                               ApplicationProperties applicationProperties) {
        this.userSettingsRepository = userSettingsRepository;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a userSettings.
     *
     * @param userSettings the entity to save
     * @return the persisted entity
     */
    public UserSettings save(UserSettings userSettings) {
        log.debug("Request to save UserSettings : {}", userSettings);
        UserSettings result = userSettingsRepository.save(userSettings);
        return result;
    }

    /**
     *  Get all the userSettings.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserSettings> findAll() {
        log.debug("Request to get all UserSettings");
        List<UserSettings> result = userSettingsRepository.findAll();

        return result;
    }

    /**
     *  Get one userSettings by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserSettings findOne(Long id) {
        log.debug("Request to get UserSettings : {}", id);
        UserSettings userSettings = userSettingsRepository.findOne(id);
        return userSettings;
    }

    /**
     *  Delete the  userSettings by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserSettings : {}", id);
        userSettingsRepository.delete(id);
    }

    /**
     * Gets settings for user.
     *
     * @param user the user
     * @return user settings
     */
    public UserSettings getForUser(User user) {
        log.debug("Request to get UserSettings by user : {}", user);
        UserSettings userSettings =  userSettingsRepository.findUserSettingsByUser(user);
        if (userSettings == null) {
            userSettings = createUserSettings(user);
        }
        return userSettings;
    }

    UserSettings createUserSettings(User user) {
        UserSettings userSettings;
        userSettings = createDefaultSettings();
        try {
            userSettingsRepository.save(userSettings);
        }
        catch (DataIntegrityViolationException e) {
            userSettings =  userSettingsRepository.findUserSettingsByUser(user);
        }
        return userSettings;
    }

    private UserSettings createDefaultSettings() {
        UserSettings userSettings = new UserSettings();
        userSettings.setMinIdleTime(applicationProperties.getDefaultMinIdleTime());
        return userSettings;
    }
}
