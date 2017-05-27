package kkot.lztimer.repository;

import kkot.lztimer.domain.Period;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Period entity.
 */
@SuppressWarnings("unused")
public interface PeriodRepository extends JpaRepository<Period,Long> {

    @Query("select period from Period period where period.owner.login = ?#{principal.username}")
    List<Period> findByOwnerIsCurrentUser();

    @Query("select period from Period period " +
        "where period.owner.login = ?1 " +
        "and period.endTime >= ?2")
    List<Period> findEndedAfter(String userLogin, Instant dateTime);
}
