package com.example.pinspired.mappers;

import java.util.List;

public interface SimpleMapper<TEntity, TDto> {
    TEntity toEntity(TDto dto);

    TDto toDto(TEntity entity);

    List<TEntity> toEntities(List<TDto> dtos);

    List<TDto> toDtos(List<TEntity> entities);
}
