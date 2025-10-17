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
public class EquippableObject {

  private String name;
  private Long strength;
  private Long power;
  private Long stealth;
  private Long range;
  private Long weight;
  private String description;
  private String type;

}
