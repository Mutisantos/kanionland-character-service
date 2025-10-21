package com.kanionland.charsheet.exp.application.handlers.creation.defaults;


import com.kanionland.charsheet.exp.application.config.StyleTemplates;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterStyle;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StylesRaceTemplateHandler extends AbstractCharacterHandler {

  private final StyleTemplates styleTemplates;

  @Override
  protected CharacterModel.CharacterModelBuilder process(
      CharacterModel.CharacterModelBuilder builder, RaceEnum race) {

    final List<CharacterStyle> incomingStyles = builder.build().getStyles();
    final List<CharacterStyle> defaultStyles = styleTemplates.getTemplates()
        .getOrDefault(race, List.of())
        .stream()
        .map(styleName -> CharacterStyle.builder()
            .name(styleName)
            .styleClass(styleName.toLowerCase().replace(" ", "-"))
            .build())
        .collect(Collectors.toList());
    if (!defaultStyles.isEmpty()) {
      List<CharacterStyle> combinedStyles = Stream.concat(
              incomingStyles.stream(),
              defaultStyles.stream()
                  .filter(newStyle -> incomingStyles.stream()
                      .noneMatch(existing -> existing.getName().equals(newStyle.getName())))
          )
          .collect(Collectors.toList());
      builder.styles(combinedStyles);
    } else {
      builder.styles(incomingStyles);
    }
    return builder;
  }
}
