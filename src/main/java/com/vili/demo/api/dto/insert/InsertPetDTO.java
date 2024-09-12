package com.vili.demo.api.dto.insert;

import com.vili.demo.domain.enums.PetType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class InsertPetDTO {

    private String name;
    private String breed;
    private Date dateOfBirth;
    private PetType type;
    private Long tutorId;
}
