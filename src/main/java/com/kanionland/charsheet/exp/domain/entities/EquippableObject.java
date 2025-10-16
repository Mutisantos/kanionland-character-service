package com.kanionland.charsheet.exp.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "equippable_objects", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class EquippableObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;
  @Column
  private Long strength;
  @Column
  private Long power;
  @Column
  private Long stealth;
  @Column
  private Long range;
  @Column
  private Long weight;
  @Column
  private String description;
  @Column
  private String type;

}
