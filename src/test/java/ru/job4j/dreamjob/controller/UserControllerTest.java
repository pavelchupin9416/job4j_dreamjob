package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRequestRegistrationPage() {
        var view = userController.getRegistrationPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    public void whenPostNewUser() {
        var user = new User(1, "test@mail.ru", "test", "password");
        when(userService.save(any())).thenReturn(Optional.of(user));
        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenPostDuplicateUser() {
        var user = new User(1, "test@mail.ru", "test", "password");
        var expectedException = new RuntimeException("Пользователь с такой почтой уже существует");
        when(userService.save(any())).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        var actualException = model.getAttribute("message");
        assertThat(view).isEqualTo("errors/404");
        assertThat(actualException).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenRequestLoginPage() {
        var view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenRequestToLogin() {
        var user = new User(1, "test@mail.ru", "test", "password");
        var request = mock(HttpServletRequest.class);
        var httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
        when(userService.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenRequestToLoginButWrongLoginOrPassword() {
        var user = new User(1, "test@mail.ru", "test", "password");
        var error = "Почта или пароль введены неверно";
        var request = mock(HttpServletRequest.class);
        when(userService.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualError = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualError).isEqualTo(error);
    }

    @Test
    public void whenRequestLogoutPage() {
        var httpSession = mock(HttpSession.class);
        var view = userController.logout(httpSession);
        assertThat(view).isEqualTo("redirect:/users/login");
    }
}
