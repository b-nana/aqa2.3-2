package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    public static void newUser(UserInfo registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String userLogin() {
        return faker.name().username();
    }

    public static String userPas() {
        return faker.internet().password();
    }

    public static UserInfo regUser(String status) {
        String login = faker.name().username();
        ;
        String password = faker.internet().password();
        UserInfo registration = new UserInfo(login, password, status);
        newUser(registration);
        return registration;
    }

}
