package com.vili.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vili.demo.core.domain.IEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Occupation  implements IEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdOn;

    private Date lastUpdatedOn;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "occupations")
    @JsonIgnoreProperties({ "document", "pets", "occupations", "nicknames" })
    private List<Tutor> tutors = new ArrayList<>();
}
