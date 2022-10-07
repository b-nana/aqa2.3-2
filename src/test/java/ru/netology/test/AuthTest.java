package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationDto;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.generateInvalidUser;
import static ru.netology.data.DataGenerator.generateValidUser;

public class AuthTest {



    @Test
    void shouldLogin() {
        RegistrationDto validUser = generateValidUser("en");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("button.button").click();
        $("h2.heading").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLoginAsBlocked() {
        RegistrationDto invalidUser = generateInvalidUser("en");
        open("http://localhost:9999/");
        $("[data-test-id=login] input").setValue(invalidUser.getLogin());
        $("[data-test-id=password] input").setValue(invalidUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        RegistrationDto validUser = generateValidUser("en");
        RegistrationDto invalidUser = generateInvalidUser("en");
        open("http://localhost:9999/");
        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(invalidUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
    }

    @Test
    void shouldNotLoginWithWrongLogin() {
        RegistrationDto validUser = generateValidUser("en");
        RegistrationDto invalidUser = generateInvalidUser("en");
        open("http://localhost:9999/");
        $("[data-test-id=login] input").setValue(invalidUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
    }
}