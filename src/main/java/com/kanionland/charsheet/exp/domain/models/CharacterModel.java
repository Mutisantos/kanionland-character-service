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
  private List<Stat> stats;
  private List<Skill> skills;
  private List<Style> styles;
  private List<Path> paths;

  public void addPart(Part part) {
    bodyParts.add(part);
  }

  public void removePart(Part part) {
    if (part != null && bodyParts.contains(part)) {
      bodyParts.remove(part);
    }
  }
}
