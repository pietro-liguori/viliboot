package com.vili.demo.api.dto;

import com.vili.demo.core.controller.IDto;
import com.vili.demo.core.domain.IEntity;
import com.vili.demo.domain.entity.Occupation;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class OccupationDTO implements IDto {

    private Long id;
    private String name;
    private String description;

    @Override
    public IEntity toEntity() {
        return Occupation.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}
