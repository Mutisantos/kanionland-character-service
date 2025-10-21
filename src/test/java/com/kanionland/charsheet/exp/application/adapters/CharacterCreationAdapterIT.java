package com.kanionland.charsheet.exp.application.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.CharacterRepository;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.PartRepository;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.SkillRepository;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.StatRepository;
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

  public static final List<String> STYLES_LIST = List.of(
      "Caballero", "Adepto", "Artista Marcial");
  public static final String CHARACTER_NAME = "Nuzz";
  public static final String CHARACTER_TITLE = "Aventurero";
  public static final String CHARACTER_GENDER = "Masculino";
  public static final int CHARACTER_AGE = 20;
  @Autowired
  private CharacterCreationAdapter adapter;

  @Autowired
  private CharacterRepository characterRepository;

  @Autowired
  private PartRepository partRepository;

  @Autowired
  private SkillRepository skillRepository;

  @Autowired
  private StatRepository statRepository;

  @Autowired
  private StyleResponse styleResponse;

  @Test
  void shouldCreateCharacterWithRaceTemplates() {
    //Given
    CreateCharacterCommand command = CreateCharacterCommand.builder()
        .name(CHARACTER_NAME)
        .race(RaceEnum.KANION)
        .title(CHARACTER_TITLE)
        .gender(CHARACTER_GENDER)
        .age(CHARACTER_AGE)
        .weight(70)
        .height(100)
        .bodyParts(List.of("Head", "Left Arm", "Right Arm", "Left Leg", "Right Leg"))
        .styles(STYLES_LIST)
        .stats(generateInitialStats())
        .build();
    //When
    CharacterBasicResponse response = adapter.createCharacter(command);
    //Then
    assertThat(response.getBodyParts()).hasSize(6); // Kanion has 6 body parts
    assertThat(
        response.getStyles().stream().map(StyleResponse::getName).toList()).containsAll(
        STYLES_LIST);
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