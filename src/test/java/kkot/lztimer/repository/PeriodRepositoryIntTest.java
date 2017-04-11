package kkot.lztimer.repository;

import kkot.lztimer.LztimerApp;
import kkot.lztimer.domain.Period;
import kkot.lztimer.domain.User;
import kkot.lztimer.util.MovingClock;
import kkot.lztimer.util.UserTestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private UserTestService userTestService;

    private MovingClock clock;

    @Before
    public void setUp() throws Exception {
        clock = new MovingClock(ZonedDateTime.now());
    }

    @Test
    public void findEndedAfter_shouldOnlyReturnPeriodsAfterDate() throws Exception {
        // arrange
        User user = userTestService.createUser("user1");
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

    @Test
    public void findEndedAfter_shouldOnlyReturnPeriodsFromUser() throws Exception {
        // arrange
        User user1 = userTestService.createUser("user1");
        User user2 = userTestService.createUser("user2");
        ZonedDateTime thresholdDate = clock.getCurrent();
        Period period1 = new Period(clock.getCurrent(), clock.shiftSeconds(1), false, user1);
        Period period2 = new Period(clock.shiftSeconds(1), clock.shiftSeconds(1), false, user2);

        periodRepositoryUnderTest.save(period1);
        periodRepositoryUnderTest.save(period2);

        // act
        List<Period> periodsUser1 = periodRepositoryUnderTest.findEndedAfter(user1.getLogin(), thresholdDate);
        List<Period> periodsUser2 = periodRepositoryUnderTest.findEndedAfter(user2.getLogin(), thresholdDate);

        // assert
        assertThat(periodsUser1, contains(period1));
        assertThat(periodsUser2, contains(period2));
    }

}
