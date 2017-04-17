package kkot.lztimer.service;

import kkot.lztimer.domain.Period;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
@Service
public class PeriodMerger {

    /**
     * @param periods list of periods in ascending order with to which new element must be merged, list must already be
     *                merged because new element is only merged to latest element of same type if possible
     * @return
     */
    public PeriodsChange merge(List<Period> periods, Period newElement) {
        if (periods.isEmpty()) {
            return new PeriodsChange(newElement);
        }

        if (!newElement.isActive()) {
            Period lastElement = periods.get(periods.size() - 1);
            if (lastElement.isActive()) {
                return new PeriodsChange(newElement);
            } else {
                Period mergedPeriod = new Period(lastElement, newElement);
                return new PeriodsChange(mergedPeriod, asList(lastElement));
            }
        } else {
            Optional<Period> lastActiveElement = periods.stream().filter(Period::isActive).reduce((a, b) -> b);
            if (lastActiveElement.isPresent()) {
                Period mergedPeriod = new Period(lastActiveElement.get(), newElement);
                int lastActiveIndex = periods.indexOf(lastActiveElement.get());
                List<Period> toRemove = periods.subList(lastActiveIndex, periods.size());
                return new PeriodsChange(mergedPeriod, toRemove);
            }
        }

        return new PeriodsChange(newElement);
    }
}
