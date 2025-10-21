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
public class CharacterStat {

  private String name;
  private String abbreviation;
  private long experience;
  private long level;
  private long mastery;
  private long bonus;
  private long penalty;
  private long limit;

}
