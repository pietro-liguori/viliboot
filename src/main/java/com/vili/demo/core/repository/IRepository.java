package com.vili.demo.core.repository;

import com.vili.demo.core.domain.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepository<T extends IEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
