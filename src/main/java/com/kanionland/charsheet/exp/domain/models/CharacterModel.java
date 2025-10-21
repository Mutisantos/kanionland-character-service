package com.kanionland.charsheet.exp.domain.models;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import java.util.List;
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
public class CharacterModel {

  private String name;
  private RaceEnum race;
  private String title;
  private String gender;
  private Long age;
  private Long weight;
  private Long height;
  private Long rankingId;
  private Ranking characterRank;
  private List<Part> bodyParts;
  private List<Item> inventory;
  private List<EquippableObject> equipment;
  private List<Skill> skills;
  private List<CharacterStyle> styles;
  private List<CharacterStat> stats;
  private List<CharacterPath> paths;

}
