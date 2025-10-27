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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String owner;

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

  @Column(nullable = false)
  private Long thirst;

  @Column(nullable = false)
  private Long hunger;

  @Column(nullable = false)
  private Long sleep;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ranking_id", nullable = false)
  private RankingEntity ranking;

  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CharacterPartEntity> bodyParts;

  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CharacterStatEntity> stats;

  @ManyToMany
  @JoinTable(
      name = "character_skills",
      joinColumns = @JoinColumn(name = "character_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id")
  )
  private Set<SkillEntity> skills;

  @ManyToMany
  @JoinTable(
      name = "character_styles",
      joinColumns = @JoinColumn(name = "character_id"),
      inverseJoinColumns = @JoinColumn(name = "style_id")
  )
  private Set<StyleEntity> styles;


}
