package com.kanionland.charsheet.exp.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "skills",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
    }
)
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column
  private String description;

  @Column
  private Long requiredExperience;

  @Column
  private Long physicalCost;

  @Column
  private Long mentalCost;

  @Column
  private Boolean isUpgradable;

  @Column
  private Long upgradeLevel;

  @Column
  private Long upgradePoints;


}
