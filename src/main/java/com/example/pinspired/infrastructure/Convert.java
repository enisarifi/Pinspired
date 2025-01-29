package com.example.pinspired.infrastructure;

public interface Convert<TDto, TEntity> {
    TDto toDto(TEntity entity);
    TEntity toEntity(TDto dto);
}
