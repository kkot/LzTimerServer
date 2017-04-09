package kkot.lztimer.service;

import kkot.lztimer.domain.Period;
import lombok.Data;

import java.util.List;

/**
 * TODO:
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
@Data
public class PeriodsChange {
    private List<Period> toAdd;
    private List<Period> toRemove;
}
