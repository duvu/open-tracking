package me.duvu.tracking.services;

import me.duvu.tracking.web.rest.model.request.ChangePasswdRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author beou on 10/16/17 23:31
 */

abstract class AbstractService<T, R> {
    abstract Page<T> getAll(String search, Pageable pageable);
    abstract List<T> getAll(String search);
    abstract T getById(Long id);
    abstract T create(R request);
    abstract T update(Long id, R request);
    abstract void delete(Long id);
}
