package com.vili.demo.domain.entity;

import com.vili.demo.domain.enums.PetType;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serial;
import java.util.Date;

@Data @EqualsAndHashCode(callSuper = true) @NoArgsConstructor
@Entity
public class Dog extends Pet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Builder
    public Dog(Long id, String name, String breed, Date dateOfBirth, PetType type, Tutor tutor, Date createdOn, Date lastUpdatedOn) {
        super(id, createdOn, lastUpdatedOn, name, breed, dateOfBirth, type, tutor);
    }

    @Override
    public PetType getType() {
        return PetType.DOG;
    }

    @Override
    public void setType(PetType type) {
        super.setType(PetType.DOG);
    }

    @Override
    public String makeNoise() {
        return "Woof!";
    }
}
