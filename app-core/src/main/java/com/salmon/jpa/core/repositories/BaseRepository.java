package com.salmon.jpa.core.repositories;

import com.salmon.jpa.core.repositories.base.BatchHandlerRepository;
import com.salmon.jpa.core.repositories.base.HibernateSearchRepository;
import com.salmon.jpa.core.repositories.base.SearchableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T,Key extends Serializable> extends JpaRepository<T,Key>,BatchHandlerRepository<T,Key>,SearchableRepository<T, Key>,HibernateSearchRepository<T> {
    EntityManager getEntityManager();
}
