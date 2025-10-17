package com.kanionland.charsheet.exp.infrastructure.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StyleResponse {

  private String name;
  private Long experience;
  private Long totalLevel;

}
