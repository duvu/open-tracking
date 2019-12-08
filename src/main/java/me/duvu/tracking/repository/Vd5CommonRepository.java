package me.duvu.tracking.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author beou on 10/16/17 20:54
 */

@NoRepositoryBean
public interface Vd5CommonRepository<T> extends ExtendedRepository<T, Long>, JpaSpecificationExecutor<T> {

}
