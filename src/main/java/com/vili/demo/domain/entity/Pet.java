package com.vili.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vili.demo.domain.enums.PetType;
import com.vili.demo.core.domain.IEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Pet implements IEntity, ICalculateAge {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdOn;

    private Date lastUpdatedOn;

    private String name;

    private String breed;

    private Date dateOfBirth;

    @Enumerated(EnumType.ORDINAL)
    private PetType type;

    @ManyToOne @JoinColumn(name = "tutor_id")
    @JsonIgnoreProperties({ "pets" })
    private Tutor tutor;

    public abstract String makeNoise();
}
