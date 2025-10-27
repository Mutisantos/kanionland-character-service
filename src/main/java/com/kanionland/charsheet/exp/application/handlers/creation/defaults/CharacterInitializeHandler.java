package com.kanionland.charsheet.exp.application.handlers.creation.defaults;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel.CharacterModelBuilder;
import java.util.HashSet;
import java.util.LinkedList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterInitializeHandler extends AbstractCharacterHandler {

  @Override
  protected CharacterModelBuilder process(final CharacterModelBuilder builder,
      final RaceEnum race) {
    return builder
        .inventory(new LinkedList<>())
        .equipment(new LinkedList<>())
        .skills(new HashSet<>())
        .paths(new HashSet<>());
  }
}
