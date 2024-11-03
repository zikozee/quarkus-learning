package com.zee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieResourceTest {


    @Order(0)
    @Test
    void create() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("FirstMovie");
        movie.setCountry("Planet");
        movie.setDescription("MyFirstMovie");
        movie.setDirector("Me");
        movie.setVersion(0L);

        Movie movie2 = new Movie();
        movie2.setTitle("SecondMovie");
        movie2.setCountry("Planet");
        movie2.setDescription("MySecondMovie");
        movie2.setDirector("Me");
        movie2.setVersion(0L);

        Movie movie3 = new Movie();
        movie3.setTitle("ThirdMovie");
        movie3.setCountry("Planet");
        movie3.setDescription("MyThirdMovie");
        movie3.setDirector("Me");
        movie3.setVersion(0L);



        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(movie))
                .when()
                .post("/movies")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(movie2))
                .when()
                .post("/movies")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(movie3))
                .when()
                .post("/movies")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @DisplayName("Server Error on Creation")
    @Order(0)
    @Test
    void createBadRequest() throws JsonProcessingException {
        Movie movie = new Movie();
        movie.setCountry("Planet");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(movie))
                .when()
                .post("/movies")
                .then()
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }


    @Test
    @Order(1)
    void getAll() {
        given()
                .when()
                .get("/movies")
                .then()
                .body("size()", equalTo(3))
                .body("id", hasItems(1,2,3))
                .body("get(0).title", equalTo("FirstMovie"))
                .body("get(0).description", equalTo("MyFirstMovie"))
                .body("get(0).director", equalTo("Me"))
                .body("get(0).country", equalTo("Planet"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Order(1)
    @Test
    void getById() {
        given()
                .when()
                .get("/movies/{id}", 1)
                .then()
                .body("id", equalTo(1))
                .body("title", equalTo("FirstMovie"))
                .body("description", equalTo("MyFirstMovie"))
                .body("director", equalTo("Me"))
                .body("country", equalTo("Planet"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @DisplayName("Get Id that doesn't exist")
    @Order(1)
    @Test
    void getByIdNotFound() {
        given()
                .when()
                .get("/movies/{id}", 100)
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Order(1)
    @Test
    void getByTitle() {
        given()
                .when()
                .get("/movies/title/{title}", "FirstMovie")
                .then()
                .body("id", equalTo(1))
                .body("title", equalTo("FirstMovie"))
                .body("description", equalTo("MyFirstMovie"))
                .body("director", equalTo("Me"))
                .body("country", equalTo("Planet"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @DisplayName("Title not found")
    @Order(1)
    @Test
    void getByTitleNotFound() {
        given()
                .when()
                .get("/movies/title/{title}", "Some_title")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Order(1)
    @Test
    void getByCountry() {
        given()
                .when()
                .get("/movies/country/{country}", "Planet")
                .then()
                .body("get(0).id", equalTo(1))
                .body("get(0).title", equalTo("FirstMovie"))
                .body("get(0).description", equalTo("MyFirstMovie"))
                .body("get(0).director", equalTo("Me"))
                .body("get(0).country", equalTo("Planet"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @DisplayName("Country not found")
    @Order(1)
    @Test
    void getByCountryNotFound() {
        given()
                .when()
                .get("/movies/country/{country}", "Saturn")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }


    @Order(3)
    @Test
    void update() throws Exception {
        Movie movie3 = new Movie();
        movie3.setTitle("ThirdMovie");
        movie3.setCountry("Planet");
        movie3.setDescription("MyThirdMovieChange");
        movie3.setDirector("Me");
        movie3.setVersion(0L);



        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(movie3))
                .when()
                .put("/movies/{id}", 3)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

    }

    @Order(3)
    @Test
    void updateNotFound() throws Exception {
        Movie movie3 = new Movie();
        movie3.setTitle("ThirdMovie");
        movie3.setCountry("Planet");
        movie3.setDescription("MyThirdMovieChange");
        movie3.setDirector("Me");
        movie3.setVersion(0L);



        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(movie3))
                .when()
                .put("/movies/{id}", 100)
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());

    }

    @Order(4)
    @Test
    void deleteById() {
        given()
                .when()
                .delete("/movies/{id}", 3)
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @DisplayName("Id not present")
    @Order(4)
    @Test
    void deleteByIdNotFound() {
        given()
                .when()
                .delete("/movies/{id}", 100)
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}