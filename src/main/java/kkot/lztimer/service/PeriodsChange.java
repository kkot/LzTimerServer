package kkot.lztimer.service;

import kkot.lztimer.domain.Period;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
public class PeriodsChange {
    private Period toAddPeriod;
    private List<Period> toRemovePeriods;

    public PeriodsChange(Period toAddPeriod) {
        this(toAddPeriod, new ArrayList<>());
    }

    public PeriodsChange(Period toAddPeriod, List<Period> toRemovePeriods) {
        this.toAddPeriod = toAddPeriod;
        this.toRemovePeriods = new ArrayList<>(toRemovePeriods);
    }

    public Period getToAddPeriod() {
        return toAddPeriod;
    }

    public List<Period> getToRemovePeriods() {
        return toRemovePeriods;
    }
}
