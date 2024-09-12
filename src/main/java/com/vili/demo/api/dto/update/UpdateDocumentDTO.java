package com.vili.demo.api.dto.update;

import com.vili.demo.domain.enums.DocumentType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class UpdateDocumentDTO {

    private String code;
    private Date expiration;
    private DocumentType type;
    private Long tutorId;
}
