package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    public static void setupUser(RegistrationDto registrationDto) {
        given().spec(requestSpec)
                .body(registrationDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().username();
    }

    public static String getPassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.internet().password();
    }

    public static RegistrationDto generateValidUser(String locale) {
        RegistrationDto registrationDto = new RegistrationDto(getLogin(locale), getPassword(locale), "active");
        setupUser(registrationDto);
        return registrationDto;
    }

    public static RegistrationDto generateInvalidUser(String locale) {
        RegistrationDto registrationDto = new RegistrationDto(getLogin(locale), getPassword(locale), "blocked");
        setupUser(registrationDto);
        return registrationDto;
    }


}
