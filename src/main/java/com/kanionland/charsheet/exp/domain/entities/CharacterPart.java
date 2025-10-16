package com.kanionland.charsheet.exp.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "character_parts")
@Getter
@Setter
public class CharacterPart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "character_id", nullable = false)
  private Character character;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "part_id", nullable = false)
  private Part part;

  @Column(name = "current_health", nullable = false)
  private Long currentHealth;

  protected CharacterPart() {
  }

  public CharacterPart(Character character, Part part) {
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
