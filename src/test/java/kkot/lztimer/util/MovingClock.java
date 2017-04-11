package kkot.lztimer.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * Simplifies getting dates that are one after another separated by some period.
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
public class MovingClock {
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
