package com.vili.demo.api.dto;

import com.vili.demo.core.controller.IDto;
import com.vili.demo.domain.entity.Document;
import com.vili.demo.core.domain.IEntity;
import com.vili.demo.domain.entity.TutorImpl;
import com.vili.demo.domain.enums.DocumentType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class DocumentDTO implements IDto {

    private Long id;
    private String code;
    private Date expiration;
    private DocumentType type;
    private Long tutorId;

    @Override
    public IEntity toEntity() {
        return Document.builder()
                .id(id)
                .code(code)
                .expiration(expiration)
                .type(type)
                .tutor(new TutorImpl(tutorId))
                .build();
    }
}
