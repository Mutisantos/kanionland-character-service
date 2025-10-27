package com.kanionland.charsheet.exp.application.configs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class RaceTemplateTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void createRaceTemplateWithValidListsShouldCreateWithCopies() {
    // Given
    List<String> bodyParts = List.of("head", "torso", "arms");
    List<String> styles = List.of("style1", "style2");

    // When
    RaceTemplate template = new RaceTemplate(bodyParts, styles);

    // Then
    assertNotNull(template);
    assertThat(template.getBodyParts()).hasSize(3);
    assertThat(template.getStyles()).hasSize(2);
  }

  @Test
  void createRaceTemplateWithNullListsShouldCreateWithEmptyLists() {
    // When
    RaceTemplate template = new RaceTemplate(null, null);

    // Then
    assertNotNull(template);
    assertTrue(template.getBodyParts().isEmpty(), "Should create empty list for null bodyParts");
    assertTrue(template.getStyles().isEmpty(), "Should create empty list for null styles");
  }

  @Test
  void raceTemplateShouldBeImmutable() {
    // Given
    List<String> bodyParts = new java.util.ArrayList<>(List.of("head"));
    List<String> styles = new java.util.ArrayList<>(List.of("style1"));
    RaceTemplate template = new RaceTemplate(bodyParts, styles);

    // When - Modify original lists
    bodyParts.add("torso");
    styles.add("style2");

    // Then
    assertEquals(1, template.getBodyParts().size(),
        "Original list modification should not affect the template");
    assertEquals(1, template.getStyles().size(),
        "Original list modification should not affect the template");

    // Test immutability of returned lists
    assertThrows(UnsupportedOperationException.class,
        () -> template.getBodyParts().add("should fail"));
    assertThrows(UnsupportedOperationException.class,
        () -> template.getStyles().add("should fail"));
  }

  @Test
  void jsonMappingShouldWorkCorrectly() throws Exception {
    // Given
    String json = """
        {
            "bodyParts": ["head", "torso"],
            "styles": ["style1"]
        }
        """;

    // When
    RaceTemplate template = objectMapper.readValue(json, RaceTemplate.class);

    // Then
    assertNotNull(template);
    assertEquals(2, template.getBodyParts().size());
    assertEquals(1, template.getStyles().size());
    assertEquals("head", template.getBodyParts().get(0));
    assertEquals("style1", template.getStyles().get(0));
  }
}
