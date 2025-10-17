package com.kanionland.charsheet.exp.application.commands;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CreateCharacterCommand {

  private String name;
  private RaceEnum race;
  private String title;
  private String gender;
  private long age;
  private long weight;
  private long height;
}
