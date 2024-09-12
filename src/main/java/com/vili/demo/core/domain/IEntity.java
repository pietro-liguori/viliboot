package com.vili.demo.core.domain;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public interface IEntity extends Serializable {

    Long getId();
    void setId(Long id);
    Date getCreatedOn();
    void setCreatedOn(Date date);
    Date getLastUpdatedOn();
    void setLastUpdatedOn(Date date);
}
