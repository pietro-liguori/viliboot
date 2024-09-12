package com.vili.demo.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true)
public class TutorImpl extends Tutor {

    private Long id;

    public TutorImpl(Long id) {
        this.id = id;
    }

    @Override
    public String speak() {
        return "Abstract tutor implementation";
    }
}
