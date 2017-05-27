package kkot.lztimer.repository;

import kkot.lztimer.domain.Period;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Period entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodRepository extends JpaRepository<Period,Long> {

    @Query("select period from Period period where period.owner.login = ?#{principal.username}")
    List<Period> findByOwnerIsCurrentUser();

}
