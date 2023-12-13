package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.UserDto;
import ru.job4j.dreamjob.dto.UserMapper;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.UserRepository;


import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public SimpleUserService(UserRepository sql2oUserRepository, UserMapper userMapper) {
        this.userRepository = sql2oUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDto> save(UserDto userDto) {
        User user = userMapper.detEntityFromDto(userDto);
        userRepository.save(user);
        return Optional.of(userMapper.getModelFromEntity(user));
    }

    @Override
    public Optional<UserDto> findByEmailAndPassword(String email, String password) {
        return Optional.of(userMapper.getModelFromEntity(userRepository.findByEmailAndPassword(email, password).get()));
    }
}
