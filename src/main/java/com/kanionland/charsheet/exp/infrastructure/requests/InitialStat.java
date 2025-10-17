package com.kanionland.charsheet.exp.infrastructure.requests;

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
public class InitialStat {

  private String name;
  private int initialLevel;
  private int initialExp;

}
