package com.kanionland.charsheet.exp.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "character_parts")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CharacterPartEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "character_id", nullable = false)
  private CharacterEntity character;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "part_id", nullable = false)
  private PartEntity part;

  @Column(name = "current_health", nullable = false)
  private Long currentHealth;

  public CharacterPartEntity(CharacterEntity character, PartEntity part) {
    this.character = character;
    this.part = part;
    this.currentHealth = part.getMaxHealth();
  }

  public void takeDamage(Long damage) {
    this.currentHealth = Math.max(0, this.currentHealth - damage);
  }

  public void heal(Long amount) {
    this.currentHealth = Math.min(part.getMaxHealth(), this.currentHealth + amount);
  }
}
