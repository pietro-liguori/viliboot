package com.vili.demo.domain.entity;

import com.vili.demo.domain.enums.PetType;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serial;
import java.util.Date;

@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
@Entity
 public class Cat extends Pet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Builder
    public Cat(Long id, String name, String breed, Date dateOfBirth, PetType type, Tutor tutor, Date createdOn, Date lastUpdatedOn) {
        super(id, createdOn, lastUpdatedOn, name, breed, dateOfBirth, type, tutor);
    }

    @Override
    public PetType getType() {
        return PetType.CAT;
    }

    @Override
    public void setType(PetType type) {
        super.setType(PetType.CAT);
    }

    @Override
    public String makeNoise() {
        return "Meow!";
    }
}
