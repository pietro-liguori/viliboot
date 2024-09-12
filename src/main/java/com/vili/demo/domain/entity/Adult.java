package com.vili.demo.domain.entity;

import com.vili.demo.domain.enums.TutorType;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
@Entity
public class Adult extends Tutor {

    @Serial
    private static final long serialVersionUID = 1L;

    @Builder
    public Adult(Long id, String name, Date dateOfBirth, TutorType type, String job, Document document, List<Pet> pets, List<Occupation> occupations, List<String> nicknames, Date createdOn, Date lastUpdatedOn) {
        super(id, createdOn, lastUpdatedOn, name, dateOfBirth, type, document, pets, occupations, nicknames);
        this.job = job;
    }

    private String job;

    @Override
    public TutorType getType() {
        return TutorType.ADULT;
    }

    @Override
    public void setType(TutorType type) {
        super.setType(TutorType.ADULT);
    }

    @Override
    public String speak() {
        return "I am the adult.";
    }
}
