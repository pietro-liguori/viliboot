package com.vili.demo.core.service;

import com.vili.demo.core.domain.IEntity;

public interface IService<T extends IEntity> extends SearchService<T>, PersistenceService<T> {

}
