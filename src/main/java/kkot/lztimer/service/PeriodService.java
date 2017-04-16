package kkot.lztimer.service;

import kkot.lztimer.domain.Period;
import kkot.lztimer.repository.PeriodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

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

    public PeriodService(PeriodRepository periodRepository, UserService userService) {
        this.periodRepository = periodRepository;
        this.userService = userService;
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

    public void addOrMerge(Period period) {

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
