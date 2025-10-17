package com.kanionland.charsheet.exp.infrastructure.mappers;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RankingEntityMapper.class})
public interface CharacterEntityMapper {

  Character toDomain(CharacterEntity entity);

  CharacterEntity toEntity(Character domain);

  List<Character> toDomainList(List<CharacterEntity> entities);

  List<CharacterEntity> toEntityList(List<Character> domains);
}
