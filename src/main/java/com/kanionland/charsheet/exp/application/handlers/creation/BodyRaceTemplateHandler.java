package com.kanionland.charsheet.exp.application.handlers.creation;


import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.Part;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BodyRaceTemplateHandler extends AbstractCharacterHandler {

  private final Map<RaceEnum, List<String>> raceBodyTemplates;

  @Override
  protected CharacterModel.CharacterModelBuilder process(
      CharacterModel.CharacterModelBuilder builder, RaceEnum race) {
    List<Part> bodyParts = raceBodyTemplates.getOrDefault(race, List.of()).stream()
        .map(partName -> Part.builder()
            .name(partName)
            .maxHealth(100L)
            .build())
        .collect(Collectors.toList());

    return builder.bodyParts(bodyParts);
  }
}
