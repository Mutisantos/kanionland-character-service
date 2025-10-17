package com.kanionland.charsheet.exp.infrastructure.persistence.entities;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "characters",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name"),
        @UniqueConstraint(columnNames = "title")
    }
)
@Getter
@Setter
public class CharacterEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RaceEnum race;

  @Column(unique = true, nullable = false)
  private String title;

  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private Long age;

  @Column(nullable = false)
  private Long weight;

  @Column(nullable = false)
  private Long height;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ranking_id", nullable = false)
  private RankingEntity ranking;

  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CharacterPartEntity> bodyParts = new HashSet<>();

  public CharacterPartEntity addPart(PartEntity part) {
    CharacterPartEntity characterPart = new CharacterPartEntity(this, part);
    bodyParts.add(characterPart);
    return characterPart;
  }

  public List<CharacterPartEntity> addParts(PartEntity part, int count) {
    List<CharacterPartEntity> addedParts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      addedParts.add(addPart(part));
    }
    return addedParts;
  }

  public void removePart(CharacterPartEntity part) {
    if (part != null && bodyParts.contains(part)) {
      bodyParts.remove(part);
      part.setCharacter(null);
    }
  }

  public List<CharacterPartEntity> getPartsOfType(PartEntity part) {
    return bodyParts.stream()
        .filter(cp -> cp.getPart().equals(part))
        .collect(Collectors.toList());
  }

  public Long getTotalCurrentHealth() {
    return bodyParts.stream()
        .mapToLong(CharacterPartEntity::getCurrentHealth)
        .sum();
  }

  public Long getTotalMaxHealth() {
    return bodyParts.stream()
        .mapToLong(cp -> cp.getPart().getMaxHealth())
        .sum();
  }
}
