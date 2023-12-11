package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;
import java.util.Properties;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }


    @AfterEach
    public void clearUsers() {
       sql2oUserRepository.deleteAllUsers();
    }

    @Test
    public void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User(0, "pavel@mail.ru", "pavel", "12345"));
        var savedUser = sql2oUserRepository.findByEmailAndPassword("pavel@mail.ru", "12345");
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }


    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var user = sql2oUserRepository.save(new User(0, "pavel@mail.ru", "pavel", "12345"));
        var isDeleted =  sql2oUserRepository.deleteAllUsers();
        var savedUser = sql2oUserRepository.findByEmailAndPassword("pavel@mail.ru", "12345");
        assertThat(isDeleted).isTrue();
        assertThat(savedUser).isEqualTo(empty());
    }

    @Test
    public void whenSaveTwoSameEmail() {
        var user1 = sql2oUserRepository.save(new User(0, "pavel@mail.ru", "pavel", "12345"));
        var user2 = sql2oUserRepository.save(new User(0, "pavel@mail.ru", "pavel", "123"));
        var savedUser1 = sql2oUserRepository.findByEmailAndPassword("pavel@mail.ru", "12345");
        assertThat(savedUser1).isEqualTo(user1);
        assertThat(user2).isEqualTo(empty());
    }
}
