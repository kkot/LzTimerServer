package kkot.lztimer.web.rest;

import com.codahale.metrics.annotation.Timed;
import kkot.lztimer.domain.UserSettings;
import kkot.lztimer.service.UserSettingsService;
import kkot.lztimer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserSettings.
 */
@RestController
@RequestMapping("/api")
public class UserSettingsResource {

    private final Logger log = LoggerFactory.getLogger(UserSettingsResource.class);

    private static final String ENTITY_NAME = "userSettings";
        
    private final UserSettingsService userSettingsService;

    public UserSettingsResource(UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }

    /**
     * POST  /user-settings : Create a new userSettings.
     *
     * @param userSettings the userSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userSettings, or with status 400 (Bad Request) if the userSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-settings")
    @Timed
    public ResponseEntity<UserSettings> createUserSettings(@Valid @RequestBody UserSettings userSettings) throws URISyntaxException {
        log.debug("REST request to save UserSettings : {}", userSettings);
        if (userSettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userSettings cannot already have an ID")).body(null);
        }
        UserSettings result = userSettingsService.save(userSettings);
        return ResponseEntity.created(new URI("/api/user-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-settings : Updates an existing userSettings.
     *
     * @param userSettings the userSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userSettings,
     * or with status 400 (Bad Request) if the userSettings is not valid,
     * or with status 500 (Internal Server Error) if the userSettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-settings")
    @Timed
    public ResponseEntity<UserSettings> updateUserSettings(@Valid @RequestBody UserSettings userSettings) throws URISyntaxException {
        log.debug("REST request to update UserSettings : {}", userSettings);
        if (userSettings.getId() == null) {
            return createUserSettings(userSettings);
        }
        UserSettings result = userSettingsService.save(userSettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userSettings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-settings : get all the userSettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userSettings in body
     */
    @GetMapping("/user-settings")
    @Timed
    public List<UserSettings> getAllUserSettings() {
        log.debug("REST request to get all UserSettings");
        return userSettingsService.findAll();
    }

    /**
     * GET  /user-settings/:id : get the "id" userSettings.
     *
     * @param id the id of the userSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userSettings, or with status 404 (Not Found)
     */
    @GetMapping("/user-settings/{id}")
    @Timed
    public ResponseEntity<UserSettings> getUserSettings(@PathVariable Long id) {
        log.debug("REST request to get UserSettings : {}", id);
        UserSettings userSettings = userSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userSettings));
    }

    /**
     * DELETE  /user-settings/:id : delete the "id" userSettings.
     *
     * @param id the id of the userSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserSettings(@PathVariable Long id) {
        log.debug("REST request to delete UserSettings : {}", id);
        userSettingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
