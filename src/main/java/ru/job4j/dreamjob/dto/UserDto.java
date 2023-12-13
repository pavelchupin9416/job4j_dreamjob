package ru.job4j.dreamjob.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {

    private int id;

    private String email;

    private String name;

    private String password;
}
