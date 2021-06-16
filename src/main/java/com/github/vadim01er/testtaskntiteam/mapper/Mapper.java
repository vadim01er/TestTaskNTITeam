package com.github.vadim01er.testtaskntiteam.mapper;

import com.github.vadim01er.testtaskntiteam.dto.AbstractDto;
import com.github.vadim01er.testtaskntiteam.entity.AbstractEntity;

public interface Mapper<ENTITY extends AbstractEntity, DTO extends AbstractDto> {
    ENTITY toEntity(DTO dto);

    DTO toDto(ENTITY entity);
}
