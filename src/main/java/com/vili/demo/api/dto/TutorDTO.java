package com.vili.demo.api.dto;

import com.vili.demo.core.controller.IDto;
import com.vili.demo.domain.entity.*;
import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.core.domain.IEntity;
import com.vili.demo.validation.constraint.ValidTutor;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data @Builder
@ValidTutor
public class TutorDTO implements IDto {

    private Long id;
    private String name;
    private Date dateOfBirth;
    private TutorType type;
    private String job;
    private String school;
    @Valid private DocumentDTO document;
    private List<Long> occupationIds;
    private List<String> nicknames;

    @Override
    public IEntity toEntity() {
        return switch (type) {
            case ADULT -> Adult.builder()
                    .id(id)
                    .name(name)
                    .dateOfBirth(dateOfBirth)
                    .type(type)
                    .job(job)
                    .nicknames(nicknames)
                    .document((Document) document.toEntity())
                    .occupations(occupationIds.stream().map(occupationId -> Occupation.builder().id(occupationId).build()).toList())
                    .build();
            case CHILD -> Child.builder()
                    .id(id)
                    .name(name)
                    .dateOfBirth(dateOfBirth)
                    .type(type)
                    .school(school)
                    .nicknames(nicknames)
                    .document((Document) document.toEntity())
                    .occupations(occupationIds.stream().map(occupationId -> Occupation.builder().id(occupationId).build()).toList())
                    .build();
        };
    }
}
