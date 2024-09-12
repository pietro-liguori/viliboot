package com.vili.demo.core.controller;

import com.vili.demo.core.domain.IEntity;
import com.vili.demo.core.request.FieldMessage;
import com.vili.demo.core.request.InvalidRequestParamException;
import com.vili.demo.core.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public interface PersistenceController<E extends IEntity, T extends IDto> {

    IService<E> getService();

    default ResponseEntity<E> insert(T dto) {
        E obj = getService().save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    default	ResponseEntity<Void> delete(Object id) {
        Long aux = null;

        try {
            aux = Long.parseLong((String) id);

            if (aux < 1)
                throw new InvalidRequestParamException(List.of(new FieldMessage("id", "Path variable must be a positive whole number")));
        } catch (NumberFormatException e) {
            throw new InvalidRequestParamException(List.of(new FieldMessage("id", "Path variable must be a positive whole number")));
        }

        getService().delete(aux);
        return ResponseEntity.noContent().build();
    }

    default ResponseEntity<E> update(T dto, Object id) {
        dto.setId((Long) id);
        E obj = getService().save(dto);
        return ResponseEntity.ok().body(obj);
    }
}
