package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjetTaskCountDto;
import com.exo1.exo1.entity.ProjetTaskCount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjetTaskCountMapper {

    ProjetTaskCountDto toDto(ProjetTaskCount projetTaskCount);
    ProjetTaskCount toEntity(ProjetTaskCountDto projetTaskCountDto);
    List<ProjetTaskCountDto> toDtos(List<ProjetTaskCount> projetTaskCountList);
    List<ProjetTaskCount> toEntities(List<ProjetTaskCountDto> projetTaskCountDtoList);
}
