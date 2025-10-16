package com.kanionland.charsheet.exp.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parts",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
    }
)
@Getter
@Setter
public class Part {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false, name = "max_health")
  private Long maxHealth;

  @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CharacterPart> characterParts = new HashSet<>();

  public CharacterPart addToCharacter(Character character) {
    CharacterPart characterPart = new CharacterPart(character, this);
    characterParts.add(characterPart);
    character.getBodyParts().add(characterPart);
    return characterPart;
  }

  public Set<CharacterPart> getCharacterParts(Character character) {
    return characterParts.stream()
        .filter(cp -> cp.getCharacter().equals(character))
        .collect(Collectors.toSet());
  }

  public void removeFromCharacter(Character character) {
    Set<CharacterPart> toRemove = characterParts.stream()
        .filter(cp -> cp.getCharacter().equals(character))
        .collect(Collectors.toSet());

    toRemove.forEach(character::removePart);
    characterParts.removeAll(toRemove);
  }
}
