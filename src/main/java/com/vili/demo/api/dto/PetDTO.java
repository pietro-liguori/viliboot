package com.vili.demo.api.dto;

import com.vili.demo.core.controller.IDto;
import com.vili.demo.domain.entity.*;
import com.vili.demo.domain.enums.PetType;
import com.vili.demo.core.domain.IEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class PetDTO implements IDto {

    private Long id;
    private String name;
    private String breed;
    private Date dateOfBirth;
    private PetType type;
    private Long tutorId;

    @Override
    public IEntity toEntity() {
        return switch (type) {
            case CAT -> Cat.builder()
                    .id(id)
                    .name(name)
                    .breed(breed)
                    .dateOfBirth(dateOfBirth)
                    .type(type)
                    .tutor(new TutorImpl(tutorId))
                    .build();
            case DOG -> Dog.builder()
                    .id(id)
                    .name(name)
                    .breed(breed)
                    .dateOfBirth(dateOfBirth)
                    .type(type)
                    .tutor(new TutorImpl(tutorId))
                    .build();
        };
    }
}
