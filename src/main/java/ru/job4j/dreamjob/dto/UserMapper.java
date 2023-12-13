package ru.job4j.dreamjob.dto;

import ru.job4j.dreamjob.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.job4j.dreamjob.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto getModelFromEntity(User user);

    User detEntityFromDto(UserDto userDto);
}
