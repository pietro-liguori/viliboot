package com.vili.demo.core.controller;

import com.vili.demo.core.domain.IEntity;
import com.vili.demo.core.request.FieldMessage;
import com.vili.demo.core.request.HttpRequestWrapper;
import com.vili.demo.core.request.InvalidRequestParamException;
import com.vili.demo.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SearchController<E extends IEntity> {

    IService<E> getService();

    Class<E> getEntityType();

    default ResponseEntity<List<E>> findAll() {
        List<E> list = getService().findAll();
        return ResponseEntity.ok().body(list);
    }

    default ResponseEntity<E> findById(Object id) {
        Long aux = null;

        try {
            aux = Long.parseLong((String) id);

            if (aux < 1)
                throw new InvalidRequestParamException(List.of(new FieldMessage("id", "Path variable must be a positive whole number")));
        } catch (NumberFormatException e) {
            if (((String) id).equals(HttpRequestWrapper.PAGE_ATTRIBUTE))
                throw new InvalidRequestParamException(List.of(new FieldMessage(HttpRequestWrapper.PAGE_ATTRIBUTE, "Must inform a page number")));
            else
                throw new InvalidRequestParamException(List.of(new FieldMessage("id", "Path variable must be a positive whole number")));
        }

        E obj = (E) getService().findById(aux);
        return ResponseEntity.ok().body(obj);
    }

    default ResponseEntity<Page<E>> findPage(HttpServletRequest request) {
        Page<E> obj = getService().findPage(new HttpRequestWrapper(request, getEntityType()));
        return ResponseEntity.ok().body(obj);
    }
}
