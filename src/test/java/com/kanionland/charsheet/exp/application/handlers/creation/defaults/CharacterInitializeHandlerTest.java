package com.kanionland.charsheet.exp.application.handlers.creation.defaults;

import static org.assertj.core.api.Assertions.assertThat;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.EquippableObject;
import com.kanionland.charsheet.exp.domain.models.Item;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterInitializeHandlerTest {

  private CharacterInitializeHandler handler;

  @BeforeEach
  void setUp() {
    handler = new CharacterInitializeHandler();
  }

  @Test
  void whenProcessThenShouldInitializeEmptyCollections() {
    // Given
    var builder = CharacterModel.builder()
        .name("Test Character")
        .race(RaceEnum.KANION);

    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();

    // Then
    assertThat(character.getInventory()).isInstanceOf(LinkedList.class).isEmpty();
    assertThat(character.getEquipment()).isInstanceOf(LinkedList.class).isEmpty();
    assertThat(character.getSkills()).isInstanceOf(LinkedList.class).isEmpty();
    assertThat(character.getPaths()).isInstanceOf(LinkedList.class).isEmpty();
  }

  @Test
  void whenProcessThenShouldNotOverrideExistingData() {
    // Given
    final Item item = Item.builder().name("Item 1").build();
    final EquippableObject equipment = EquippableObject.builder().name("Equipment 1").build();
    var builder = CharacterModel.builder()
        .name("Test Character")
        .race(RaceEnum.KANION)
        .inventory(List.of(item))
        .equipment(List.of(equipment))
        .skills(Set.of())
        .paths(Set.of());

    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();

    // Then
    assertThat(character.getInventory()).isEmpty();
    assertThat(character.getEquipment()).isEmpty();
    assertThat(character.getSkills()).isEmpty();
    assertThat(character.getPaths()).isEmpty();
  }
}
