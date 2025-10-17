package com.kanionland.charsheet.exp.infrastructure.responses;

import java.util.List;

public class CharacterBasicResponse {

  private String name;
  private String race;
  private String title;
  private String gender;
  private Long age;
  private Long weight;
  private Long height;
  private Long rankingId;
  private Long characterRank;
  private List<String> inventory;
  private List<String> skills;
  private List<CharacterPartResponse> bodyParts;
  private List<CharacterEquipResponse> equipment;
  private List<CharacterPathResponse> paths;
  private List<StatResponse> stats;
  private List<StyleResponse> styles;

}
