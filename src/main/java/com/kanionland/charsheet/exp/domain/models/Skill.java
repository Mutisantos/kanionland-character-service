package com.kanionland.charsheet.exp.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

  private String name;
  private String description;
  private Long requiredExperience;
  private Long physicalCost;
  private Long mentalCost;
  private Boolean isUpgradable;
  private Long upgradeLevel;
  private Long upgradePoints;

}
