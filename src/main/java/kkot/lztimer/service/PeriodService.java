package kkot.lztimer.service;

import kkot.lztimer.domain.Period;
import kkot.lztimer.domain.User;
import kkot.lztimer.domain.UserSettings;
import kkot.lztimer.repository.PeriodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing Period.
 */
@Service
@Transactional
public class PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodService.class);

    private final PeriodRepository periodRepository;

    private final UserService userService;

    private final UserSettingsService userSettingsService;

    private final PeriodMerger periodMerger;

    public PeriodService(PeriodRepository periodRepository, UserService userService,
                         UserSettingsService userSettingsService, PeriodMerger periodMerger) {
        this.periodRepository = periodRepository;
        this.userService = userService;
        this.userSettingsService = userSettingsService;
        this.periodMerger = periodMerger;
    }

    /**
     * Save a period.
     *
     * @param period the entity to save
     * @return the persisted entity
     */
    public Period save(Period period) {
        log.debug("Request to save Period : {}", period);
        period.setOwner(userService.getUserWithAuthorities());
        Period result = periodRepository.save(period);
        return result;
    }

    /**
     * Adds new period or merge it with existing period and adds merged period after removing previously existing.
     *
     * @param period period to add or merge
     */
    public void addOrMerge(Period period) {
        User user = userService.getUserWithAuthorities();
        UserSettings userSettings = userSettingsService.getForUser(user);
        Instant searchAfter = period.getBeginTime().minusSeconds(userSettings.getMinIdleTime());
        List<Period> recentPeriods = periodRepository.findEndedAfter(user.getLogin(), searchAfter);
        PeriodsChange periodsChange = periodMerger.merge(recentPeriods, period);

        for (Period periodToRemove : periodsChange.getToRemovePeriods()) {
            delete(periodToRemove.getId());
        }
        save(periodsChange.getToAddPeriod());
    }

    /**
     *  Get all the periods.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Period> findAll() {
        log.debug("Request to get all Periods");
        List<Period> result = periodRepository.findAll();

        return result;
    }

    /**
     *  Get one period by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Period findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        Period period = periodRepository.findOne(id);
        return period;
    }

    /**
     *  Delete the  period by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.delete(id);
    }
}
