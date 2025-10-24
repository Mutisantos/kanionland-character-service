package com.kanionland.charsheet.exp.application.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Value;

@Value
public class RaceTemplate {

  List<String> bodyParts;
  List<String> styles;

  public RaceTemplate(
      @JsonProperty("bodyParts") List<String> bodyParts,
      @JsonProperty("styles") List<String> styles
  ) {
    this.bodyParts = bodyParts != null ? List.copyOf(bodyParts) : List.of();
    this.styles = styles != null ? List.copyOf(styles) : List.of();
  }
}

