package com.vili.demo.core.service;

import com.vili.demo.core.domain.IEntity;
import com.vili.demo.core.exception.ResourceNotFoundException;
import com.vili.demo.core.repository.IRepository;
import com.vili.demo.core.request.HttpRequestWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SearchService<T extends IEntity> {

    IRepository<T> getRepository();

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default T findById(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    default Page<T> findPage(HttpRequestWrapper request) {
        Specification<T> spec = request.getSpecification();

        if (spec == null)
            return getRepository().findAll(request.getPageRequest());

        return getRepository().findAll(spec, request.getPageRequest());
    }
}
