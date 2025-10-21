package com.kanionland.charsheet.exp.infrastructure.persistence.entities;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatRaceId implements Serializable {

  private String statId;

  @Enumerated(EnumType.STRING)
  private RaceEnum race;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatRaceId that = (StatRaceId) o;
    return Objects.equals(statId, that.statId) && race == that.race;
  }

  @Override
  public int hashCode() {
    return Objects.hash(statId, race);
  }
}