package com.vili.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vili.demo.domain.enums.DocumentType;
import com.vili.demo.core.domain.IEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Document implements IEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdOn;

    private Date lastUpdatedOn;

    private String code;

    private Date expiration;

    @Enumerated(EnumType.ORDINAL)
    private DocumentType type;

    @OneToOne(mappedBy = "document")
    @JsonIgnoreProperties({ "document", "pets", "occupations", "nicknames" })
    private Tutor tutor;
}
