package com.kanionland.charsheet.exp.infrastructure.persistence.repositories;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {

  Optional<SkillEntity> findByName(String name);

  @Query("SELECT part FROM SkillEntity part WHERE part.name IN :names")
  List<SkillEntity> findByNameIn(@Param("names") Collection<String> names);
}
