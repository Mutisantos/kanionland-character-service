package com.kanionland.charsheet.exp.application.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.application.mappers.CharacterMapper;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.CharacterRepository;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterQueryAdapterTest {

  @Mock
  private CharacterRepository characterRepository;

  @Mock
  private CharacterMapper characterMapper;

  @InjectMocks
  private CharacterQueryAdapter adapter;


  @Test
  void createCharacter_ShouldProcessCorrectly() {
    // Given
    when(characterRepository.findAllByOwner("BeakLimit")).thenReturn(List.of(
        CharacterEntity.builder().build()));
    when(characterMapper.toResponse(any())).thenReturn(
        CharacterBasicResponse.builder().owner("BeakLimit").build());
    // When
    List<CharacterBasicResponse> result = adapter.getAllUserCharacters("BeakLimit");
    // Then
    assertThat(result).isNotEmpty();
    assertThat(result.get(0).getOwner()).isEqualTo("BeakLimit");
  }

}