package com.automation.tests.assignment;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.automation.pojos.Character;
import com.automation.pojos.House;
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
    static String  key;

    @BeforeAll
    public static void startUp() {
        key = "$2a$10$kFn7hhS2HAIoBhQX8CcumOJhV58NFfKaunq83jLlLUZEEGwHIf2F.";
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
                queryParam("key", key ).
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
                queryParam("key", key).
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
                queryParam("key", key).
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
                    queryParam("key", key).
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

        JsonPath js =
        given().
                accept(ContentType.JSON).
                queryParam("key", key).
            when().
                get("/houses").//prettyPeek().
            then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").extract().jsonPath();
        List<Object>members=null;
        String houseId=null;
        int i  = 0;
        for (; i < 4; i++) {// this loop is looking for where Gryffindor is located and takes the order numbera as "i"
            if (js.getString("name["+i+"]").equals("Gryffindor")){
                houseId=js.getString("_id["+i+"]");  //in the Gryffindor json object we grab the members and house  id`s
                members = js.getList("members["+i+"]");
            }
        }

        given().
                accept(ContentType.JSON).
                pathParam("id",houseId).
                queryParam("key", key).
            when().
                get("/houses/{id}").
            then().assertThat().body("_id["+i+"]",is(houseId)).body("members._id",containsInAnyOrder(members));
    }

    @Test
    @DisplayName("Verify house members again")
    public void Test9() {
        JsonPath house =
                given().
                        accept(ContentType.JSON).
                        queryParam("key", key).
                        pathParam("id", "5a05e2b252f721a3cf2ea33f").
                        when().
                        get("/houses/{id}").//then().log().all();//prettyPeek().
                        jsonPath();

        House gryffindor = house.getObject("[0]", House.class);

        List<Character> characters =
                given().accept(ContentType.JSON).
                        queryParam("key", key).
                        queryParam("house", "Gryffindor").
                        when().
                        get("/characters").jsonPath().getList("", Character.class);

        List<String> id_in_gryffin = new ArrayList<>();
        for (Map mapOfCharacter : gryffindor.getMembers()) {
            id_in_gryffin.add(mapOfCharacter.get("_id").toString());
        }
        for (Character ch : characters) {
            Assertions.assertTrue(id_in_gryffin.contains(ch.getId()), "person couldn`t found :" + ch.getName() + " " + ch.getId());
        }
    }
        @Test
        @DisplayName("Verify Gryffindor has most largest member")
        public void Test10() {

            JsonPath js =
                    given().
                            accept(ContentType.JSON).
                            queryParam("key", key).
                            when().
                            get("/houses").//prettyPeek().
                            then().
                            assertThat().
                            statusCode(200).
                            contentType("application/json; charset=utf-8").extract().jsonPath();

            int membersSize = 0 ;
            String houseName = null;

            for (int i = 0; i < 4; i++) {// this loop is looking for where Gryffindor is located and takes the order numbera as "i"
                int num = js.getList("members["+i+"]").size();
                    if(num>membersSize){
                        membersSize = num ;
                        houseName = js.get("name["+i+"]");
            }
                Assertions.assertTrue(houseName.equalsIgnoreCase("Gryffindor"));

        }
    }
}


