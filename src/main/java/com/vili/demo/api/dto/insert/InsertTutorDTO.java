package com.vili.demo.api.dto.insert;

import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.validation.insert.constraint.ValidInsertTutor;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data @Builder
@ValidInsertTutor
public class InsertTutorDTO {

    private String name;
    private Date dateOfBirth;
    private TutorType type;
    private String job;
    private String school;
    @Valid private InsertDocumentDTO document;
    private List<Long> occupationIds;
    private List<String> nicknames;
}
