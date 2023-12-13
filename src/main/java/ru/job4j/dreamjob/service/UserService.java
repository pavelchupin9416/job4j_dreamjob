package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.dto.UserDto;
import ru.job4j.dreamjob.model.User;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> save(UserDto userDto);

    Optional<UserDto> findByEmailAndPassword(String email, String password);
}