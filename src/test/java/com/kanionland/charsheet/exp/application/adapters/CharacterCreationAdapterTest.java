package com.kanionland.charsheet.exp.application.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.StyleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterCreationTest {

  @Autowired
  private CharacterCreationAdapter adapter;

  @Test
  void shouldCreateCharacterWithRaceTemplates() {
    CreateCharacterCommand command =
        new CreateCharacterCommand(
            "CharacterTest", RaceEnum.ZI_BUM, "Adventurer", "Male", 25, 70, 180);

    CharacterBasicResponse response = adapter.createCharacter(command);

    assertThat(response.getBodyParts()).hasSize(5); // ZI_BUM has 5 body parts
    assertThat(response.getStyles().stream().map(StyleResponse::getName).toList()).contains(
        "Cientifico", "Tirador");
  }
}