package com.kanionland.charsheet.exp.infrastructure.requests;

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
public class CreateCharacterRequest {

  private String name;
  private String race;
  private String title;
  private String gender;
  private Long age;
  private Long weight;
  private Long height;
  private List<String> bodyParts;
  private List<String> styles;
  private List<InitialStat> stats;

}
