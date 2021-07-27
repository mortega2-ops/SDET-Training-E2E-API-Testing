package test;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.get;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import java.io.IOException;
import org.junit.Test;

public class RestAssuredTests {

  JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory
      .newBuilder().setValidationConfiguration(
          ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();

//  @Before
//  public void beforeMethod() {
//
//  }

  @Test
  public void givenUrl_whenSuccessOnResponseJsonConformsToSchema_thenCorrect() throws IOException {
    get("http://localhost:3000/todos")
        .then().statusCode(200)
        .assertThat()
        .body(matchesJsonSchemaInClasspath("todos-schema.json").using(jsonSchemaFactory));
  }
}
