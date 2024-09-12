package com.vili.demo.api.dto.update;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UpdateOccupationDTO {

    private String name;
    private String description;
}
