package com.kanionland.charsheet.exp.infrastructure.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class CharacterBasicResponse {

  private String name;
  private String race;
  private String title;
  private String gender;
  private int age;
  private int weight;
  private int height;
  private int rankingId;
  private int characterRank;
  private List<String> inventory;
  private List<String> skills;
  private List<CharacterPartResponse> bodyParts;
  private List<CharacterEquipResponse> equipment;
  private List<CharacterPathResponse> paths;
  private List<StatResponse> stats;
  private List<StyleResponse> styles;

}
