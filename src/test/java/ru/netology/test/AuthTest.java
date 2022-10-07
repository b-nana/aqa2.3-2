package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class AuthTest {
    @BeforeEach
    void shouldOpenWebApp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogin() {
        UserInfo user = DataGenerator.regUser("active");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();
        $("h2.heading").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLoginAsBlocked() {
        UserInfo user = DataGenerator.regUser("blocked");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        UserInfo user = DataGenerator.regUser("active");
        $("[data-test-id=login] input").setValue(DataGenerator.userLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWithWrongLogin() {
        UserInfo user = DataGenerator.regUser("active");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(DataGenerator.userPas());
        $("button.button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

}