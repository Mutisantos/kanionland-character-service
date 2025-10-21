package com.kanionland.charsheet.exp.infrastructure.persistence.entities;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stat_race_definitions",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"stat_id", "race"})
    }
)
@Getter
@Setter
public class StatRaceEntity {

  @EmbeddedId
  private StatRaceId id = new StatRaceId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("statId")
  @JoinColumn(name = "stat_id", nullable = false)
  private StatEntity stat;

  @Enumerated(EnumType.STRING)
  @Column(name = "race", nullable = false, insertable = false, updatable = false)
  private RaceEnum race;

  @Column(name = "race_limit", nullable = false)
  private Long raceLimit;

  @Column(name = "race_experience_multiplier", nullable = false, precision = 4, scale = 2)
  private BigDecimal raceExperienceMultiplier;

  public void setStat(StatEntity stat) {
    this.stat = stat;
    if (id == null) {
      id = new StatRaceId();
    }
    id.setStatId(stat != null ? stat.getId() : null);
  }

  public void setRace(RaceEnum race) {
    this.race = race;
    if (id == null) {
      id = new StatRaceId();
    }
    id.setRace(race);
  }

}
