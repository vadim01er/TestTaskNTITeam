package com.github.vadim01er.testtaskntiteam.mapper;

import com.github.vadim01er.testtaskntiteam.dto.LordDto;
import com.github.vadim01er.testtaskntiteam.entity.Lord;
import org.springframework.stereotype.Component;

@Component
public class LordMapper extends AbstractMapper<Lord, LordDto>{
    public LordMapper() {
        super(Lord.class, LordDto.class);
    }
}
