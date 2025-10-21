package com.kanionland.charsheet.exp.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "character_stats")
@Getter
@Setter
public class CharacterStatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "character_id", nullable = false)
  private CharacterEntity character;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stat_id", nullable = false)
  private StatEntity stat;

  @Column(nullable = false)
  private Long experience;

  @Column(nullable = false)
  private Long level;

  @Column(nullable = false)
  private Long mastery;

  @Column(nullable = false)
  private Long bonus;

  @Column(nullable = false)
  private Long penalty;

  @Column(nullable = false)
  private Long statLimit;

  public CharacterStatEntity(CharacterEntity character, StatEntity stat, long initialLevel,
      long initialExp, long initialLimit) {
    this.character = character;
    this.stat = stat;
    this.experience = initialExp;
    this.level = initialLevel;
    this.statLimit = initialLimit;
    this.mastery = 0L;
    this.bonus = 0L;
    this.penalty = 0L;
  }

}
