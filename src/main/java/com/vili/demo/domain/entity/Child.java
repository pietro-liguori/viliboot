package com.vili.demo.domain.entity;

import com.vili.demo.domain.enums.TutorType;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
@Entity
public class Child extends Tutor {

    @Serial
    private static final long serialVersionUID = 1L;

    @Builder
    public Child(Long id, String name, Date dateOfBirth, TutorType type, String school, Document document, List<Pet> pets, List<Occupation> occupations, List<String> nicknames, Date createdOn, Date lastUpdatedOn) {
        super(id, createdOn, lastUpdatedOn, name, dateOfBirth, type, document, pets, occupations, nicknames);
        this.school = school;
    }

    private String school;

    @Override
    public TutorType getType() {
        return TutorType.CHILD;
    }

    @Override
    public void setType(TutorType type) {
        super.setType(TutorType.CHILD);
    }

    @Override
    public String speak() {
        return "I am the child.";
    }
}
