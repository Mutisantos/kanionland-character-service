package com.kanionland.charsheet.exp.infrastructure.persistence.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stats",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
    }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatEntity {

  @Id
  @Column(unique = true, nullable = false, length = 3)
  private String id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  private Long levelUpExperience;

  @OneToMany(mappedBy = "stat", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CharacterStatEntity> characterStats;

  @OneToMany(mappedBy = "stat", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<StatRaceEntity> raceConstraints;

}
