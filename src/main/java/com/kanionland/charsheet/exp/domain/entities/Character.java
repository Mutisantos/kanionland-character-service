package com.kanionland.charsheet.exp.domain.entities;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
public class Character {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
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
  private Ranking ranking;

}
