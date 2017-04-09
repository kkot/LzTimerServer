package kkot.lztimer.service;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * TODO:
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
public class PeriodMergerTest {

    PeriodMerger priodMerger = new PeriodMerger();

    @Test
    public void shouldReturnNoChanges_whenOnlyOne() {
        // Given
        PeriodsChange change = priodMerger.merge(Arrays.asList());

        // When

        // Then
    }


}
