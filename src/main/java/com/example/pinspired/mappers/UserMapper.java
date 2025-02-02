package com.example.pinspired.mappers;


import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.PostEntity;
import com.example.pinspired.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserMapper {

    @Mapping(source = "savedPosts", target = "savedPostIds")
    @Mapping(target = "profilePicture", expression = "java( (entity.getProfilePicture() == null || entity.getProfilePicture().isEmpty()) ? \"/images/icons/no_pfp.png\" : entity.getProfilePicture() )")
    UserDto toDto(UserEntity entity);

    @Mapping(target = "savedPosts", ignore = true)
    UserEntity toEntity(UserDto dto);

    // Remove the convertToDto method - it's redundant with the toDto mapping

    @Mapping(target = "profilePicture", expression = "java( (registerUserRequestDto.getProfilePicture() == null || registerUserRequestDto.getProfilePicture().isEmpty()) ? \"/images/icons/no_pfp.png\" : registerUserRequestDto.getProfilePicture() )")
    @Mapping(target = "birthdate", expression = "java(registerUserRequestDto.getBirthdate() != null ? java.time.format.DateTimeFormatter.ISO_LOCAL_DATE.format(registerUserRequestDto.getBirthdate()) : null)")
    UserEntity userRequestDtoToUser(RegisterUserRequestDto registerUserRequestDto);

    default List<Long> mapSavedPosts(List<PostEntity> posts) {
        return posts.stream()
                .map(PostEntity::getId)
                .collect(Collectors.toList());
    }
}