package com.kanionland.charsheet.exp.infrastructure.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatResponse {

  private String name;
  private Long experience;
  private Long totalLevel;

}
