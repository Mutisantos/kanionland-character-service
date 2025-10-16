package com.kanionland.charsheet.exp.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "stats",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
    }
)
public class Stat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true, nullable = false, length = 3)
  private String abbreviation;

  @Column(nullable = false)
  private Long levelUpExperience;

}
