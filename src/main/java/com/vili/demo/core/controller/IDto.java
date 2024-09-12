package com.vili.demo.core.controller;

import com.vili.demo.core.domain.IEntity;

public interface IDto {

    Long getId();
    void setId(Long id);
    IEntity toEntity();

    default boolean isInsert() {
        return getId() == null;
    }

    default boolean isUpdate() {
        return getId() != null;
    }

}
