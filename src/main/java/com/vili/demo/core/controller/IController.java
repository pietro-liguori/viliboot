package com.vili.demo.core.controller;

import com.vili.demo.core.domain.IEntity;

public interface IController<E extends IEntity, T extends IDto> extends SearchController<E>, PersistenceController<E, T> {

}