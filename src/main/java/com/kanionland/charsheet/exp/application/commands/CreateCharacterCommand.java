package com.kanionland.charsheet.exp.application.commands;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.infrastructure.requests.InitialStat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CreateCharacterCommand {

  private String name;
  private RaceEnum race;
  private String title;
  private String gender;
  private long age;
  private long weight;
  private long height;
  private List<String> bodyParts;
  private List<String> styles;
  private List<InitialStat> stats;
}
