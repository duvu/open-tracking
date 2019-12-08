package me.duvu.tracking.web.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * @author beou on 10/16/17 22:49
 */

public abstract class Vd5AdminController<I, O> {
    abstract Page<O> getAll(String search, Pageable pageable);
    abstract List<O> getAll(String search);
    abstract O getById(Long id);
    abstract O create(I request, BindingResult result);
    abstract void update(Long id, I request, BindingResult result);
    abstract void delete(Long id);
}
