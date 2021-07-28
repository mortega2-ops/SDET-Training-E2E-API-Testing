package io.catalyte.restAssured.test;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.catalyte.restAssured.Todo;
import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestAssuredTests {

  final String ID = "1234567";

  JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory
      .newBuilder().setValidationConfiguration(
          ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();

  @Test
  public void test_3_getAllTodos() throws IOException {
    given()
    .when()
        .get("http://localhost:3000/todos/")
    .then()
        .statusCode(200)
        .assertThat()
        .body(matchesJsonSchemaInClasspath("todos-schema.json").using(jsonSchemaFactory));
  }

  @Test
  public void test_4_deleteSpecificTodoAndEnsureItIsDeleted() {
    given()
        .when()
        .delete("http://localhost:3000/todos/" + ID)
        .then()
        .statusCode(200);

    given()
        .when()
        .get("http://localhost:3000/todos/" + ID)
        .then()
        .statusCode(404);
  }

  @Test
  public void test_1_deleteAllTodosThenGetAllTodosAndEnsureAnEmptyArrayIsReturned() {
    given()
    .when()
        .delete("http://localhost:3000/todos")
    .then()
        .statusCode(204);

    given()
    .when()
        .get("http://localhost:3000/todos/")
    .then()
        .statusCode(200)
    .and()
        .assertThat()
        .body(equalTo("[]"));
  }

  @Test
  public void test_2_postTodo() {
    Todo todo = new Todo("Walk the dog.", false, ID);
    given()
        .contentType("application/json")
        .body(todo.toString())
    .when()
        .post("http://localhost:3000/todos/")
    .then()
        .statusCode(201);

    given()
        .when()
        .get("http://localhost:3000/todos/" + ID)
        .then()
        .statusCode(200)
        .and()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("todo-schema.json").using(jsonSchemaFactory));
  }

}
