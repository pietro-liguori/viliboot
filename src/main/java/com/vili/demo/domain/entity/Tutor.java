package com.vili.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.core.domain.IEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Inheritance(strategy = InheritanceType.JOINED)
public abstract class Tutor implements IEntity, ICalculateAge {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdOn;

    private Date lastUpdatedOn;

    private String name;

    private Date dateOfBirth;

    @Enumerated(EnumType.ORDINAL)
    private TutorType type;

    @OneToOne @JoinColumn(name = "document_id")
    @JsonIgnoreProperties({ "tutor" })
    private Document document;

    @OneToMany(mappedBy = "tutor")
    @JsonIgnoreProperties({ "tutor" })
    private List<Pet> pets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tutor_occupation", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "occupation_id"))
    @JsonIgnoreProperties({ "tutors" })
    private List<Occupation> occupations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "nicknames")
    private List<String> nicknames = new ArrayList<>();

    @JsonGetter
    public abstract String speak();
}
