package kkot.lztimer.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Instant;

/**
 * Simplifies getting dates that are one after another separated by some period.
 *
 * @author Krzysztof Kot (krzysztof.kot.pl@gmail.com)
 */
public class MovingClock {
    private Instant current = Instant.now();

    public MovingClock(Instant current) {
        this.current = current;
    }

    public Instant shiftSeconds(int seconds) {
        current = current.plusSeconds(seconds);
        return current;
    }

    public Instant getCurrent() {
        return current;
    }
}
