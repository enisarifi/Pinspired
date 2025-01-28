package com.example.pinspired.mappers;

import com.example.pinspired.dtos.ListingPostDto;
import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.entities.PostEntity;
import com.example.pinspired.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Primary
@Mapper(componentModel = "spring")
public interface PostMapper extends SimpleMapper<PostEntity, PostDto> {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Override
    @Mapping(source = "userId", target = "userEntity.id")
    PostEntity toEntity(PostDto postDto);

    @Override
    @Mapping(source = "userEntity.id", target = "userId")
    PostDto toDto(PostEntity postEntity);

    @Mapping(source = "userEntity.id", target = "userId")
    @Mapping(source = "createdAt", target = "postedAt")
    @Mapping(source = "userEntity", target = "fullName", qualifiedByName = "fullName")
    @Mapping(source = "createdAt", target = "postedAgo", qualifiedByName = "timeAgo")
    ListingPostDto toListingPostDto(PostEntity postEntity);

    @Named("fullName")
    default String fullName(UserEntity userEntity) {
        return userEntity.getName() + " " + userEntity.getSurname();
    }

    @Named("timeAgo")
    default String timeAgo(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Mapping(target = "userEntity.id", source = "userId")
    List<PostEntity> toEntities(List<PostDto> dtos);

    @Override
    @Mapping(target = "userEntity.id", source = "userId")
    List<PostDto> toDtos(List<PostEntity> entities);
}

