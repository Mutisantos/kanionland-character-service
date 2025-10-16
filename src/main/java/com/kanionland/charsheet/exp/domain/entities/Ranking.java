package com.kanionland.charsheet.exp.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ranking_levels")
@Getter
@Setter
public class Ranking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "level", unique = true, nullable = false)
  private Long level;

  @Column(name = "points_required", nullable = false)
  private Long pointsRequired;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "ranking", cascade = {CascadeType.PERSIST,
      CascadeType.MERGE}, orphanRemoval = false)
  private Set<Character> characters = new HashSet<>();

  public void addCharacter(Character character) {
    characters.add(character);
    character.setRanking(this);
  }

  public void removeCharacter(Character character) {
    characters.remove(character);
    character.setRanking(null);
  }
}
