package com.kanionland.charsheet.exp.application.config;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "characters.templates")
@Getter
@Setter
public class CharacterTemplatesProperties {

  @NotNull
  private Map<RaceEnum, RaceTemplate> races = new EnumMap<>(RaceEnum.class);

  @Getter
  @Setter
  @NoArgsConstructor
  public static class RaceTemplate {

    private List<String> bodyParts = new ArrayList<>();
    private List<String> styles = new ArrayList<>();
  }
}
