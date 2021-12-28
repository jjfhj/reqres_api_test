package com.github.jjfhj;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void getListUser() {
        given()
                .when()
                .get("api/users?page=1")
                .then().log().all()
                .statusCode(200)
                .body("page", is(1),
                        "data[0].id", is(1),
                        "data[5].avatar", notNullValue(),
                        "support.text", is("To keep ReqRes free, " +
                                "contributions towards server costs are appreciated!"));
    }

    @Test
    void getSingleUser() {
        String response =
                get("api/users/2")
                        .then().log().all()
                        .extract().path("data.email");

        assertThat(response).isEqualTo("janet.weaver@reqres.in");
    }

    @Test
    void postCreateUser() {

        String data = "{ \"name\": \"Matthew Frank\", \"job\": \"Analyst\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("api/users")
                .then().log().all()
                .statusCode(201)
                .body("name", is("Matthew Frank"),
                        "job", is("Analyst"),
                        "id", notNullValue(),
                        "createdAt", notNullValue());
    }

    @Test
    void putUpdateUser() {

        String data = "{ \"name\": \"Matthew Frank\", \"job\": \"Business Analyst\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .put("api/users/2")
                .then().log().all()
                .statusCode(200)
                .body("name", is("Matthew Frank"),
                        "job", is("Business Analyst"),
                        "updatedAt", notNullValue());
    }

    @Test
    void patchUpdateUser() {

        String data = "{ \"name\": \"Matthew Moore\", \"job\": \"Business Analyst\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .put("api/users/2")
                .then().log().all()
                .statusCode(200)
                .body("name", is("Matthew Moore"),
                        "job", is("Business Analyst"),
                        "updatedAt", notNullValue());
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("api/users/2")
                .then().log().all()
                .statusCode(204);
    }
}
