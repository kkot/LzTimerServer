package kkot.lztimer.repository;

import kkot.lztimer.LztimerApp;
import kkot.lztimer.domain.Period;
import kkot.lztimer.domain.User;
import kkot.lztimer.service.UserService;
import kkot.lztimer.util.MovingClock;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Integration test for {@link PeriodRepository}.
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LztimerApp.class)
@Transactional
public class PeriodRepositoryIntTest {

    @Autowired
    private PeriodRepository periodRepositoryUnderTest;

    @Autowired
    private UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    private MovingClock clock;

    @Before
    public void setUp() throws Exception {
        clock = new MovingClock(ZonedDateTime.now());
        periodRepositoryUnderTest.deleteAll();
    }

    @Test
    public void findEndedAfter_shouldOnlyReturnPeriodsAfterDate() throws Exception {
        // arrange
        User user = userService.createUser("johndoe", "johndoe", "John", "Doe", "" +
            "john.doe@localhost", "http://placehold.it/50x50", "en-US", true);

        Period beforePeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), false, user);
        ZonedDateTime thresholdDate = clock.shiftSeconds(1);
        Period afterPeriod = new Period(clock.shiftSeconds(1), clock.shiftSeconds(1), false, user);

        periodRepositoryUnderTest.save(beforePeriod);
        periodRepositoryUnderTest.save(afterPeriod);

        // act
        List<Period> periodsAfter = periodRepositoryUnderTest.findEndedAfter(user.getLogin(), thresholdDate);

        // assert
        assertThat(periodsAfter, contains(afterPeriod));
    }

}
