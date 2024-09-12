package com.vili.demo.api.dto.insert;

import com.vili.demo.domain.enums.DocumentType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class InsertDocumentDTO {

    private String code;
    private Date expiration;
    private DocumentType type;
}
