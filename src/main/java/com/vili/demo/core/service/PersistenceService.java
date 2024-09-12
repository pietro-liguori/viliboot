package com.vili.demo.core.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Optional;

import com.vili.demo.core.domain.IEntity;
import com.vili.demo.core.exception.DatabaseException;
import com.vili.demo.core.exception.NoSuchEntityException;
import com.vili.demo.core.exception.ResourceNotFoundException;
import com.vili.demo.core.repository.IRepository;
import com.vili.demo.core.controller.IDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

public interface PersistenceService<T extends IEntity> {

    IRepository<T> getRepository();

    default T save(T entity) {
        if (entity == null)
            throw new NoSuchEntityException("Entity can't be null");

        entity.setLastUpdatedOn(new Date());

        if (entity.getId() != null) {
            Optional<T> temp = getRepository().findById(entity.getId());

            if (temp.isPresent())
                entity = update(temp.get(), entity);
            else
                entity.setCreatedOn(new Date());
        } else {
            entity.setCreatedOn(new Date());
        }

        return getRepository().save(entity);
    }

    @SuppressWarnings("unchecked")
    default T save(IDto dto) {
        if (dto == null)
            throw new NoSuchEntityException("Entity can't be null");

        T entity = (T) dto.toEntity();
        entity.setLastUpdatedOn(new Date());

        if (entity.getId() != null) {
            Optional<T> temp = getRepository().findById(entity.getId());

            if (temp.isPresent())
                entity = update(temp.get(), entity);
            else
                entity.setCreatedOn(new Date());
        } else {
            entity.setCreatedOn(new Date());
        }

        return getRepository().save(entity);
    }

    default void delete(Long id) {
        try {
            getRepository().deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    default T update(T oldEntity, T newEntity) {
        for (Field field : newEntity.getClass().getDeclaredFields()) {
            if (!field.getName().equals("id") && !field.getName().equals("createdAt")) {
                try {
                    Object oldValue = field.get(oldEntity);
                    Object newValue = field.get(newEntity);

                    if (!newValue.equals(oldValue))
                        field.set(oldEntity, newValue);
                } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
                    // TODO
                }
            }
        }

        return oldEntity;
    }
}
