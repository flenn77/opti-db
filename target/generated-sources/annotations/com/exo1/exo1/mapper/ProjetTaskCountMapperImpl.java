package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjetTaskCountDto;
import com.exo1.exo1.entity.ProjetTaskCount;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-13T20:06:37+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProjetTaskCountMapperImpl implements ProjetTaskCountMapper {

    @Override
    public ProjetTaskCountDto toDto(ProjetTaskCount projetTaskCount) {
        if ( projetTaskCount == null ) {
            return null;
        }

        ProjetTaskCountDto projetTaskCountDto = new ProjetTaskCountDto();

        projetTaskCountDto.setName( projetTaskCount.getName() );
        if ( projetTaskCount.getTaskCount() != null ) {
            projetTaskCountDto.setTaskCount( projetTaskCount.getTaskCount() );
        }

        return projetTaskCountDto;
    }

    @Override
    public ProjetTaskCount toEntity(ProjetTaskCountDto projetTaskCountDto) {
        if ( projetTaskCountDto == null ) {
            return null;
        }

        ProjetTaskCount projetTaskCount = new ProjetTaskCount();

        projetTaskCount.setName( projetTaskCountDto.getName() );
        projetTaskCount.setTaskCount( projetTaskCountDto.getTaskCount() );

        return projetTaskCount;
    }

    @Override
    public List<ProjetTaskCountDto> toDtos(List<ProjetTaskCount> projetTaskCountList) {
        if ( projetTaskCountList == null ) {
            return null;
        }

        List<ProjetTaskCountDto> list = new ArrayList<ProjetTaskCountDto>( projetTaskCountList.size() );
        for ( ProjetTaskCount projetTaskCount : projetTaskCountList ) {
            list.add( toDto( projetTaskCount ) );
        }

        return list;
    }

    @Override
    public List<ProjetTaskCount> toEntities(List<ProjetTaskCountDto> projetTaskCountDtoList) {
        if ( projetTaskCountDtoList == null ) {
            return null;
        }

        List<ProjetTaskCount> list = new ArrayList<ProjetTaskCount>( projetTaskCountDtoList.size() );
        for ( ProjetTaskCountDto projetTaskCountDto : projetTaskCountDtoList ) {
            list.add( toEntity( projetTaskCountDto ) );
        }

        return list;
    }
}
