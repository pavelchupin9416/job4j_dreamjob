package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndexControllerTest {
    private IndexController indexController = new IndexController();

    @Test
    public void whenRequestMainPage() {
        var view = indexController.getIndex();

        assertThat(view).isEqualTo("index");
    }
}
