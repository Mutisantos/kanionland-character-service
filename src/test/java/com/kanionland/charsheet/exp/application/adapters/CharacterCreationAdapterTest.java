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
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterPartResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.StatResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.StyleResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterCreationAdapterTest {

  public static final String CHAR_NAME = "Test Character";
  public static final String CHAR_TITLE = "Adventurer";
  public static final String CHAR_GENDER = "Male";
  public static final int CHAR_AGE = 25;
  public static final int CHAR_WEIGHT = 70;
  public static final int CHAR_HEIGHT = 180;
  public static final List<String> BODY_PARTS = List.of("Head",
      "Torso");
  public static final List<String> STYLES = List.of("Warrior",
      "Mage");
  public static final InitialStat STRENGTH_STAT = new InitialStat("Strength", 1, 0, 10);
  public static final InitialStat INT_STAT = new InitialStat("Intelligence", 1, 0, 10);
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
  private CharacterModelBuilder modelBuilder;
  private CharacterBasicResponse response;

  @BeforeEach
  void setUp() {
    command = CreateCharacterCommand.builder()
        .name(CHAR_NAME)
        .race(RaceEnum.KANION)
        .title(CHAR_TITLE)
        .gender(CHAR_GENDER)
        .age(CHAR_AGE)
        .weight(CHAR_WEIGHT)
        .height(CHAR_HEIGHT)
        .bodyParts(BODY_PARTS)
        .styles(STYLES)
        .stats(List.of(STRENGTH_STAT, INT_STAT))
        .build();

    modelBuilder = CharacterModel.builder()
        .name(command.getName())
        .race(command.getRace())
        .title(command.getTitle())
        .gender(command.getGender())
        .age(command.getAge())
        .weight(command.getWeight())
        .height(command.getHeight());

    response = CharacterBasicResponse.builder()
        .name(CHAR_NAME)
        .race(RaceEnum.KANION.toString())
        .title(CHAR_TITLE)
        .gender(CHAR_GENDER)
        .age(CHAR_AGE)
        .weight(CHAR_WEIGHT)
        .height(CHAR_HEIGHT)
        .bodyParts(List.of(new CharacterPartResponse("Head", 0, 1),
            new CharacterPartResponse("Torso", 0, 1)))
        .styles(List.of(new StyleResponse("Warrior", 0, 1),
            new StyleResponse("Mage", 0, 1)))
        .stats(List.of(new StatResponse("Strength", 0, 1),
            new StatResponse("Intelligence", 0, 1)))
        .build();
  }

  @Test
  void createCharacter_ShouldProcessCorrectly() {
    // Given
    final CharacterEntity mockEntity = CharacterEntity.builder().name(CHAR_NAME).build();
    when(characterDefaultsChain.getInitialChainHandler())
        .thenReturn(defaultsHandler);
    when(defaultsHandler.handle(any(CharacterModelBuilder.class), eq(RaceEnum.KANION)))
        .thenReturn(modelBuilder);
    when(characterMapper.toEntity(any(CharacterModel.class)))
        .thenReturn(mockEntity);
    when(characterRelationsChain.getInitialChainHandler()).thenReturn(relationsHandler);
    when(relationsHandler.handle(any(CharacterEntity.class), any(CharacterModel.class)))
        .thenReturn(mockEntity);
    when(characterRepository.save(mockEntity)).thenReturn(mockEntity);
    when(characterMapper.toResponse(mockEntity)).thenReturn(response);
    // When
    CharacterBasicResponse result = characterCreationAdapter.createCharacter(command);
    // Then
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo(CHAR_NAME);

    verify(characterDefaultsChain).getInitialChainHandler();
    verify(defaultsHandler).handle(any(CharacterModelBuilder.class), eq(RaceEnum.KANION));
    verify(characterMapper).toEntity(any(CharacterModel.class));
    verify(characterRelationsChain).getInitialChainHandler();
    verify(relationsHandler).handle(any(CharacterEntity.class), any(CharacterModel.class));
    verify(characterRepository).save(mockEntity);
    verify(characterMapper).toResponse(mockEntity);
  }

}