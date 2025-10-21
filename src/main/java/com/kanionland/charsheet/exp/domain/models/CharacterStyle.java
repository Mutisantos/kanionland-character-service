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
public class CharacterStyle {

  private String name;
  private String styleClass;

  @Builder.Default
  private long rankLevel = 0L;

  @Builder.Default
  private long usedExperience = 0L;
}
