package kkot.lztimer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.security.acl.Owner;
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

    public Period() {
    }

    public Period(ZonedDateTime beginTime, ZonedDateTime endTime, Boolean active) {
        this(beginTime, endTime, active, null);
    }

    public Period(ZonedDateTime beginTime, ZonedDateTime endTime, Boolean active, User owner) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.active = active;
        this.owner = owner;
    }

    public Period(Period previous, Period next) {
        if (previous.isActive() != next.isActive()) {
            throw new IllegalStateException("Merging active with inactive");
        }
        this.beginTime = previous.beginTime;
        this.endTime = next.endTime;
        this.active = previous.active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "begin_time", nullable = false)
    private ZonedDateTime beginTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @JsonIgnore
    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBeginTime() {
        return beginTime;
    }

    public Period beginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public void setBeginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Period endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
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
        return period.beginTime.equals(this.beginTime)
            && period.endTime.equals(this.endTime)
            && period.active == this.active
            && Objects.equals(period.owner, this.owner);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(beginTime)
            .append(endTime)
            .append(active)
            .append(owner).build();
    }

    @Override
    public String toString() {
        return "Period{" +
            "id=" + id +
            ", beginTime='" + beginTime + "'" +
            ", endTime='" + endTime + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
