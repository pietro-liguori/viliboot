package com.vili.demo.api.dto.update;

import com.vili.demo.domain.enums.PetType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class UpdatePetDTO {

    private String name;
    private String breed;
    private Date dateOfBirth;
    private PetType type;
    private Long tutorId;
}
