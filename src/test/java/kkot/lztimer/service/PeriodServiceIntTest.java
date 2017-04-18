package kkot.lztimer.service;

import kkot.lztimer.LztimerApp;
import kkot.lztimer.domain.Period;
import kkot.lztimer.domain.User;
import kkot.lztimer.repository.PeriodRepository;
import kkot.lztimer.security.SecurityService;
import kkot.lztimer.util.MovingClock;
import kkot.lztimer.util.UserTestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration tests for {@link PeriodService}.
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = LztimerApp.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class PeriodServiceIntTest {

    @Autowired
    private PeriodService periodServiceSUT;

    @Autowired
    private PeriodRepository periodRepository;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private UserTestService userTestService;

    private MovingClock clock;

    @Value("${application.default-min-idle-time}")
    private int minIdleTime;

    @Before
    public void setUp() throws Exception {
        clock = new MovingClock(ZonedDateTime.now());
    }

    @Test
    public void shouldMergeAndRemoveExisting_whenExistingAreInRangeAndBelongToCurrentUser() throws Exception {
        // arrange
        User userLogged = userTestService.createUser("user1");
        User userOther = userTestService.createUser("user2");
        Mockito.when(securityService.getCurrentUserLogin()).thenReturn(userLogged.getLogin());

        Period active1 = new Period(clock.getCurrent(), clock.shiftSeconds(1), true, userLogged);
        Period idle2 = new Period(clock.getCurrent(), clock.shiftSeconds(1), false, userLogged);
        Period idle2other = new Period(idle2.getBeginTime(), idle2.getEndTime(), false, userOther);
        periodRepository.save(Arrays.asList(active1, idle2, idle2other));

        Period newActive3 = new Period(clock.shiftSeconds(1), clock.shiftSeconds(1), true, userLogged);

        // act
        periodServiceSUT.addOrMerge(newActive3);

        // assert
        Period newMergedActive = new Period(active1.getBeginTime(), newActive3.getEndTime(), true, userLogged);
        assertThat(periodRepository.findAll()).containsExactlyInAnyOrder(idle2other, newMergedActive);
    }

    @Test
    public void shouldNotMerge_whenActivePeriodsDistanceIsLongerThenMinimal() throws Exception {
        // arrange
        User userLogged = userTestService.createUser("user1");
        Mockito.when(securityService.getCurrentUserLogin()).thenReturn(userLogged.getLogin());

        Period active1 = new Period(clock.getCurrent(), clock.shiftSeconds(1), true, userLogged);
        periodRepository.save(Arrays.asList(active1));

        Period newActive2 = new Period(clock.shiftSeconds(minIdleTime + 1), clock.shiftSeconds(1), true, userLogged);

        // act
        periodServiceSUT.addOrMerge(newActive2);

        // assert
        assertThat(periodRepository.findAll()).containsExactlyInAnyOrder(active1, newActive2);
    }

    @Test
    public void shouldMerge_whenActivePeriodsDistanceIsShorterThenMinimal() throws Exception {
        // arrange
        User userLogged = userTestService.createUser("user1");
        Mockito.when(securityService.getCurrentUserLogin()).thenReturn(userLogged.getLogin());

        Period active1 = new Period(clock.getCurrent(), clock.shiftSeconds(1), true, userLogged);
        periodRepository.save(Arrays.asList(active1));

        Period newActive2 = new Period(clock.shiftSeconds(minIdleTime - 1), clock.shiftSeconds(1), true, userLogged);

        // act
        periodServiceSUT.addOrMerge(newActive2);

        // assert
        assertThat(periodRepository.findAll()).containsExactly(
            new Period(active1.getBeginTime(), newActive2.getEndTime(), true, userLogged));
    }
}
