package com.automation.tests.assignment;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class HaryPotterApi {


    @BeforeAll
    public static void startUp() {

        baseURI = ConfigurationReader.getProperty("potterapi");
    }

    @Test
    @DisplayName("Verify Sorting Hat")
    public void Test1() {
        Response response =
            when().
                get("/sortingHat");
        response.
            then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8");

        Assertions.assertTrue(List.of("Slytherin", "Ravenclaw", "Hufflepuff", "Gryffindor").
                contains(response.getBody().
                        asString().
                        replace("\"","")),
                "hat name is not recognized");


    }

    @Test
    @DisplayName("Verify Bad Key")
    public void Test2() {

        given().
                accept(ContentType.JSON).
                queryParam("key", "invalid").
            when().
                get("/characters").
            then().
                assertThat().
                statusCode(401).
                contentType("application/json; charset=utf-8").
                statusLine(containsString("Unauthorized")).
                body("error", is("API Key Not Found"));

    }

    @Test
    @DisplayName("Verify No Key")
    public void Test3() {

        given().
                accept(ContentType.JSON).
            when().
                get("/characters").
            then().
                assertThat().
                statusCode(409).
                contentType("application/json; charset=utf-8").
                statusLine(containsString("Conflict")).
                body("error", is("Must pass API key for request"));

    }

    @Test
    @DisplayName("Verify Number of characters")
    public void Test4() {

        given().
                accept(ContentType.JSON).
                queryParam("key", "$2a$10$kFn7hhS2HAIoBhQX8CcumOJhV58NFfKaunq83jLlLUZEEGwHIf2F.").
            when().
                get("/characters").
            then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                body("", hasSize(195));

    }

    @Test
    @DisplayName("Verify Number of character id and house")
    public void Test5() {

        given().
                accept(ContentType.JSON).
                queryParam("key", "$2a$10$kFn7hhS2HAIoBhQX8CcumOJhV58NFfKaunq83jLlLUZEEGwHIf2F.").
            when().
                get("/characters").
            then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                body("_id", everyItem(not(emptyString()))).
                body("dumbledoresArmy", everyItem(is(instanceOf(boolean.class)))).
                body("house", everyItem(is(in(Arrays.asList("Slytherin", "Ravenclaw", "Hufflepuff", "Gryffindor", null)))));
    }


    @Test
    @DisplayName("Verify character information")
    public void Test6() {

        given().
                accept(ContentType.JSON).
                queryParam("key", "$2a$10$kFn7hhS2HAIoBhQX8CcumOJhV58NFfKaunq83jLlLUZEEGwHIf2F.").
                queryParam("name", "Ludo Bagman").
            when().
                get("/characters").
            then().
                assertThat().
                    statusCode(200).
                    contentType("application/json; charset=utf-8").
                    body("_id", hasItem("5a0fa5deae5bc100213c2330")).
                    body("name", hasItem("Ludo Bagman")).
                    body("role", hasItem("Head, Department of Magical Games and Sports")).
                    body("__v", hasItem(0)).
                    body("ministryOfMagic", hasItem(true)).
                    body("orderOfThePhoenix", hasItem(false)).
                    body("dumbledoresArmy", hasItem(false)).
                    body("deathEater", hasItem(false)).
                    body("bloodStatus",hasItem("unknown")).
                    body("species",hasItem("human"));
    }
    @Test
    @DisplayName("Verify name search")
    public void Test7() {
        String[] people = {"Harry Potter", "Marry Potter"};
        Response response;
//        String person = people[1];
        for (String person : people) {
            response = given().
                    accept(ContentType.JSON).
                    queryParam("key", "$2a$10$kFn7hhS2HAIoBhQX8CcumOJhV58NFfKaunq83jLlLUZEEGwHIf2F.").
                    queryParam("name", person).
                    when().
                    get("/characters");
            response.then().assertThat().
                    statusCode(200).
                    contentType("application/json; charset=utf-8");
            if (person.equals("Harry Potter")) {
                response.then().assertThat().body("name", hasItem("Harry Potter"));
            } else {
                response.then().assertThat().body("", hasSize(0));
            }
        }
    }
    @Test
    @DisplayName("Verify house members")
    public void Test8() {
        String key = "$2a$10$kFn7hhS2HAIoBhQX8CcumOJhV58NFfKaunq83jLlLUZEEGwHIf2F.";
        JsonPath js =
        given().
                accept(ContentType.JSON).
                queryParam("key", key).
            when().
                get("/houses").
            then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").extract().jsonPath();
        List<Object>members=null;
        String houseId=null;

        for (int i = 0; i < 4; i++) {
            if (js.getString("name["+i+"]").equals("Gryffindor")){
                houseId=js.getString("_id["+i+"]");
                members = js.getList("members["+i+"]");
            }
        }
        given().
                accept(ContentType.JSON).
                pathParam("id",houseId).
                queryParam("key", key).
            when().
                get("/houses/{id}").
            then().assertThat().body("_id[0]",is(houseId)).body("members._id",containsInAnyOrder(members));
    }
}


