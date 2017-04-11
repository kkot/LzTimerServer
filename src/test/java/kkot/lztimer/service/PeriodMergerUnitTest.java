package kkot.lztimer.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import kkot.lztimer.domain.Period;
import kkot.lztimer.util.MovingClock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for {@link PeriodMerger}.
 *
 * @author Krzysztof Kot (krzykot@gmail.com)
 */
@RunWith(JUnitParamsRunner.class)
public class PeriodMergerUnitTest {

    private PeriodMerger periodMerger = new PeriodMerger();
    private MovingClock clock;

    @Before
    public void setUp() throws Exception {
        periodMerger = new PeriodMerger();
        clock = new MovingClock(ZonedDateTime.of(2015,1,1, 12, 0, 0,
            0, ZoneId.systemDefault()));
    }

    @Test
    public void shouldAddPeriod_whenNoPeriodsBefore() {
        Period newPeriod = new Period();
        PeriodsChange change = periodMerger.merge(asList(), newPeriod);
        assertThat(change.getToAddPeriod(), is(newPeriod));
        assertThat(change.getToRemovePeriods(), empty());
    }

    @Parameters({ "false", "true" })
    @Test
    public void shouldAddPeriod_whenOneExistingIsDifferentActivity(boolean active) {
        Period oldPeriod = new Period(clock.shiftSeconds(1), clock.shiftSeconds(1), active);
        Period newPeriod = new Period(clock.shiftSeconds(1), clock.shiftSeconds(1), !active);
        PeriodsChange change = periodMerger.merge(asList(oldPeriod), newPeriod);
        assertThat(change.getToAddPeriod(), is(newPeriod));
        assertThat(change.getToRemovePeriods(), empty());
    }

    @Parameters({ "false", "true" })
    @Test
    public void shouldMerge_whenLastOneIfSameType(boolean active) {
        // arrange
        Period oldPeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), active);
        Period newPeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), active);

        // act
        PeriodsChange change = periodMerger.merge(asList(oldPeriod), newPeriod);

        // assert
        Period merged = new Period(oldPeriod, newPeriod);
        assertThat(change.getToAddPeriod(), is(merged));
        assertThat(change.getToRemovePeriods(), is(asList(oldPeriod)));
    }

    @Test
    public void shouldNotMergeInactive_whenSeparatedByActive() throws Exception {
        // arrange
        Period idlePeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), false);
        Period activePeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), true);
        Period newIdlePeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), false);

        // act
        PeriodsChange change = periodMerger.merge(asList(idlePeriod, activePeriod), newIdlePeriod);

        // assert
        assertThat(change.getToAddPeriod(), is(newIdlePeriod));
        assertThat(change.getToRemovePeriods(), is(empty()));
    }

    @Test
    public void shouldMergeActiveAndRemoveIdle_whenIdleInBetween() throws Exception {
        // arrange
        Period idlePeriod1 = new Period(clock.getCurrent(), clock.shiftSeconds(1), false);
        Period activePeriod2 = new Period(clock.getCurrent(), clock.shiftSeconds(1), true);
        Period idlePeriod3 = new Period(clock.getCurrent(), clock.shiftSeconds(1), false);
        Period newActive = new Period(clock.getCurrent(), clock.shiftSeconds(1), true);

        // act
        PeriodsChange change = periodMerger.merge(asList(idlePeriod1, activePeriod2, idlePeriod3), newActive);

        // assert
        Period merged = new Period(activePeriod2, newActive);
        assertThat(change.getToAddPeriod(), equalTo(merged));
        assertThat(change.getToRemovePeriods(), contains(activePeriod2, idlePeriod3));
    }
}
