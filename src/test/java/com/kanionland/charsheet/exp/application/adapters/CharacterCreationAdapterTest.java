package com.kanionland.charsheet.exp.application.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.handlers.creation.defaults.CharacterDefaultsChain;
import com.kanionland.charsheet.exp.application.handlers.creation.defaults.CharacterDefaultsHandler;
import com.kanionland.charsheet.exp.application.handlers.creation.persistence.CharacterRelationsChain;
import com.kanionland.charsheet.exp.application.handlers.creation.persistence.CharacterRelationsHandler;
import com.kanionland.charsheet.exp.application.mappers.CharacterMapper;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterModel.CharacterModelBuilder;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.CharacterRepository;
import com.kanionland.charsheet.exp.infrastructure.requests.InitialStat;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterCreationAdapterTest {

  @Mock
  private CharacterRepository characterRepository;

  @Mock
  private CharacterMapper characterMapper;

  @Mock
  private CharacterDefaultsChain characterDefaultsChain;

  @Mock
  private CharacterRelationsChain characterRelationsChain;

  @Mock
  private CharacterDefaultsHandler defaultsHandler;

  @Mock
  private CharacterRelationsHandler relationsHandler;

  @InjectMocks
  private CharacterCreationAdapter characterCreationAdapter;

  private CreateCharacterCommand command;
  private CharacterModel model;
  private CharacterEntity entity;
  private CharacterEntity savedEntity;
  private CharacterBasicResponse response;

  @BeforeEach
  void setUp() {
    command = CreateCharacterCommand.builder()
        .name("Test Character")
        .race(RaceEnum.KANION)
        .title("Adventurer")
        .gender("Male")
        .age(25)
        .weight(70L)
        .height(180L)
        .bodyParts(List.of("Head", "Torso"))
        .styles(List.of("Warrior", "Mage"))
        .stats(List.of(
            new InitialStat("Strength", 1, 0, 10),
            new InitialStat("Intelligence", 1, 0, 10)
        ))
        .build();

    CharacterModelBuilder modelBuilder = CharacterModel.builder()
        .name(command.getName())
        .race(command.getRace())
        .title(command.getTitle())
        .gender(command.getGender())
        .age(command.getAge())
        .weight(command.getWeight())
        .height(command.getHeight());

    model = modelBuilder.build();

    entity = new CharacterEntity();
    savedEntity = new CharacterEntity();
    savedEntity.setId(1L);

    response = new CharacterBasicResponse();
    response.setId(1L);
    response.setName("Test Character");
  }

  @Test
  void createCharacter_ShouldProcessCorrectly() {
    // Given
    when(characterDefaultsChain.getInitialChainHandler()).thenReturn(defaultsHandler);
    when(defaultsHandler.handle(any(CharacterModelBuilder.class), eq(RaceEnum.KANION)))
        .thenReturn(CharacterModel.builder().from(model));

    when(characterMapper.toEntity(any(CharacterModel.class))).thenReturn(entity);

    when(characterRelationsChain.getInitialChainHandler()).thenReturn(relationsHandler);
    when(relationsHandler.handle(any(CharacterEntity.class), any(CharacterModel.class)))
        .thenReturn(entity);

    when(characterRepository.save(entity)).thenReturn(savedEntity);
    when(characterMapper.toResponse(savedEntity)).thenReturn(response);

    // When
    CharacterBasicResponse result = characterCreationAdapter.createCharacter(command);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getName()).isEqualTo("Test Character");

    // Verify interactions
    verify(characterDefaultsChain).getInitialChainHandler();
    verify(defaultsHandler).handle(any(CharacterModelBuilder.class), eq(RaceEnum.KANION));
    verify(characterMapper).toEntity(any(CharacterModel.class));
    verify(characterRelationsChain).getInitialChainHandler();
    verify(relationsHandler).handle(any(CharacterEntity.class), any(CharacterModel.class));
    verify(characterRepository).save(entity);
    verify(characterMapper).toResponse(savedEntity);
  }

  @Test
  void buildCharacter_ShouldMapCommandToModelCorrectly() {
    // When
    CharacterModel result = characterCreationAdapter.buildCharacter(command).build();

    // Then
    assertThat(result.getName()).isEqualTo(command.getName());
    assertThat(result.getRace()).isEqualTo(command.getRace());
    assertThat(result.getTitle()).isEqualTo(command.getTitle());
    assertThat(result.getGender()).isEqualTo(command.getGender());
    assertThat(result.getAge()).isEqualTo(command.getAge());
    assertThat(result.getWeight()).isEqualTo(command.getWeight());
    assertThat(result.getHeight()).isEqualTo(command.getHeight());
    assertThat(result.getBodyParts()).hasSize(2);
    assertThat(result.getStyles()).hasSize(2);
    assertThat(result.getStats()).hasSize(2);
  }
}