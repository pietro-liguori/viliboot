package com.vili.demo.api.dto.insert;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class InsertOccupationDTO {

    private String name;
    private String description;
}
