package com.github.jjfhj.tests;

import com.github.jjfhj.models.SingleUserData;
import org.junit.jupiter.api.Test;

import static com.github.jjfhj.specs.Specs.request;
import static com.github.jjfhj.specs.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTest {

    @Test
    void getListUsers() {
        given()
                .spec(request)
                .when()
                .get("/users?page=1")
                .then()
                .spec(responseSpec)
                .body("page", is(1),
                        "data[0].id", is(1),
                        "data[5].avatar", notNullValue(),
                        "support.text", is("To keep ReqRes free, " +
                                "contributions towards server costs are appreciated!"));
    }

    @Test
    void getSingleUser() {
        String response =
                given()
                        .spec(request)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpec)
                        .extract().path("data.email");

        assertThat(response).isEqualTo("janet.weaver@reqres.in");
    }

    @Test
    void getSingleUserWithModel() {
        SingleUserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .extract().as(SingleUserData.class);

        assertThat(data.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
    }

    @Test
    void postCreateUser() {

        String data = "{ \"name\": \"Matthew Frank\", \"job\": \"Analyst\" }";

        given()
                .spec(request)
                .body(data)
                .when()
                .post("/users")
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
                .spec(request)
                .body(data)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec)
                .body("name", is("Matthew Frank"),
                        "job", is("Business Analyst"),
                        "updatedAt", notNullValue());
    }

    @Test
    void patchUpdateUser() {

        String data = "{ \"name\": \"Matthew Moore\", \"job\": \"Business Analyst\" }";

        given()
                .spec(request)
                .body(data)
                .when()
                .patch("/users/2")
                .then()
                .spec(responseSpec)
                .body("name", is("Matthew Moore"),
                        "job", is("Business Analyst"),
                        "updatedAt", notNullValue());
    }

    @Test
    void deleteUser() {
        given()
                .spec(request)
                .when()
                .delete("/users/2")
                .then().log().all()
                .statusCode(204);
    }
}
