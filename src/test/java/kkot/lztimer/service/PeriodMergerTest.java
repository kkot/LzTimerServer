package kkot.lztimer.service;

import kkot.lztimer.domain.Period;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * TODO:
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
public class PeriodMergerTest {

    private PeriodMerger periodMerger = new PeriodMerger();
    private MovingClock clock;

    class MovingClock {
        private ZonedDateTime current = ZonedDateTime.now();

        public MovingClock(ZonedDateTime current) {
            this.current = current;
        }

        public ZonedDateTime shiftSeconds(int seconds) {
            current = current.plusSeconds(seconds);
            return current;
        }

        public ZonedDateTime getCurrent() {
            return current;
        }
    }

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

    private void shouldAddPeriod_whenOneExistingIsDifferentActivity(boolean active) {
        Period oldPeriod = new Period(clock.shiftSeconds(1), clock.shiftSeconds(1), active);
        Period newPeriod = new Period(clock.shiftSeconds(1), clock.shiftSeconds(1), !active);
        PeriodsChange change = periodMerger.merge(asList(oldPeriod), newPeriod);
        assertThat(change.getToAddPeriod(), is(newPeriod));
        assertThat(change.getToRemovePeriods(), empty());
    }

    @Test
    public void shouldAddPeriod_whenOneExistingIsDifferentActivityActive() {
        shouldAddPeriod_whenOneExistingIsDifferentActivity(true);
    }

    @Test
    public void shouldAddPeriod_whenOneExistingIsDifferentActivityNotActive() {
        shouldAddPeriod_whenOneExistingIsDifferentActivity(false);
    }

    @Test
    public void shouldMerge_whenLastOneIfSameTypeActive() {
        shouldMerge_whenLastOneIfSameType(true);
    }

    @Test
    public void shouldMerge_whenLastOneIfSameTypeInactive() {
        shouldMerge_whenLastOneIfSameType(false);
    }

    public void shouldMerge_whenLastOneIfSameType(boolean active) {
        Period oldPeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), active);
        Period newPeriod = new Period(clock.getCurrent(), clock.shiftSeconds(1), active);
        PeriodsChange change = periodMerger.merge(asList(oldPeriod), newPeriod);
        Period merged = new Period(oldPeriod, newPeriod);

        assertThat(change.getToAddPeriod(), is(merged));
        assertThat(change.getToRemovePeriods(), is(asList(oldPeriod)));

    }
}
