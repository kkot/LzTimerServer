package kkot.lztimer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Period.
 */
@Entity
@Table(name = "period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NotNull
    @Column(name = "stop_time", nullable = false)
    private ZonedDateTime stopTime;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Period startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getStopTime() {
        return stopTime;
    }

    public Period stopTime(ZonedDateTime stopTime) {
        this.stopTime = stopTime;
        return this;
    }

    public void setStopTime(ZonedDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public Boolean isActive() {
        return active;
    }

    public Period active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getOwner() {
        return owner;
    }

    public Period owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Period period = (Period) o;
        if (period.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, period.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Period{" +
            "id=" + id +
            ", startTime='" + startTime + "'" +
            ", stopTime='" + stopTime + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
