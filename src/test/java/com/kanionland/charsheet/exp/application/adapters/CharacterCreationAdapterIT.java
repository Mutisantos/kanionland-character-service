package com.kanionland.charsheet.exp.application.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.infrastructure.requests.InitialStat;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.StyleResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CharacterCreationAdapterIT {

  @Autowired
  private CharacterCreationAdapter adapter;

  @Test
  void shouldCreateCharacterWithRaceTemplates() {
    CreateCharacterCommand command = CreateCharacterCommand.builder()
        .name("Nuzz")
        .race(RaceEnum.KANION)
        .title("Aventurero")
        .gender("Masculino")
        .age(20)
        .weight(70)
        .height(100)
        .bodyParts(List.of("Head", "Left Arm", "Right Arm", "Left Leg", "Right Leg"))
        .styles(List.of("Caballero", "Adepto", "Artista Marcial"))
        .stats(generateInitialStats())
        .build();

    CharacterBasicResponse response = adapter.createCharacter(command);

    assertThat(response.getBodyParts()).hasSize(5); // ZI_BUM has 5 body parts
    assertThat(response.getStyles().stream().map(StyleResponse::getName).toList()).contains(
        "Cientifico", "Tirador");
  }

  private List<InitialStat> generateInitialStats() {
    return List.of(
        InitialStat.builder().name("Vitalidad").initialExp(1000).initialLevel(10).build(),
        InitialStat.builder().name("Mana").initialExp(1000).initialLevel(5).build(),
        InitialStat.builder().name("Fuerza").initialExp(1000).initialLevel(10).build(),
        InitialStat.builder().name("Defensa").initialExp(1000).initialLevel(10).build(),
        InitialStat.builder().name("Conjuracion").initialExp(1000).initialLevel(5).build(),
        InitialStat.builder().name("Inteligencia").initialExp(1000).initialLevel(5).build(),
        InitialStat.builder().name("Equilibrio").initialExp(1000).initialLevel(5).build());
  }

}