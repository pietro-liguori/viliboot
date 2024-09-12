package com.vili.demo.api.dto.update;

import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.validation.update.constraint.ValidUpdateTutor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
@ValidUpdateTutor
public class UpdateTutorDTO {

    private String name;
    private String job;
    private String school;
    private Date dateOfBirth;
    private TutorType type;
}
