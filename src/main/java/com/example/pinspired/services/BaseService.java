package com.example.pinspired.services;

import java.util.List;

// T - Entity, TId - Entity's ID (mapping behet ne kontroller)
// T - DTO, TId - DTO's ID (Profen) (mappimi behet ne service)
public interface BaseService<T, TId> {
    List<T> findAll();

    T findById(TId id);

    T add(T model);

    T modify(TId id, T model);

    void removeById(TId id);

}
