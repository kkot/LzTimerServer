package kkot.lztimer.service;

import kkot.lztimer.domain.Period;

import java.util.List;

/**
 * Created by krzysiek on 08.04.2017.
 */
public class PeriodMerger {

    public PeriodsChange merge(List<Period> periods) {
        if (periods.isEmpty()) {
            return new PeriodsChange();
        }
        return null;
    }
}
