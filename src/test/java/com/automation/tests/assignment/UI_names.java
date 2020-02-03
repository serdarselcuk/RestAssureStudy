package com.automation.tests.assignment;

import com.automation.pojos.Spartan;
import com.automation.utilities.APIUtilities;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.annotations.SerializedName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UI_names {

    @BeforeAll
    public static void startuo(){
        baseURI = "https://uinames.com/api/";
    }
    @Test
    public void test1(){

        given().
                accept(ContentType.JSON).
        when().
                get().
        then().
                assertThat().
                    statusCode(200).
                    contentType("application/json; charset=utf-8").
                    body("", is(notNullValue()));
    }
    @Test
    public void test2() {

        given().
                accept(ContentType.JSON).
                queryParam("gender", "male").
        when().
                get().
        then().
                assertThat().
                    statusCode(200).
                    contentType("application/json; charset=utf-8").
                    body("gender",is("male"));
    }
    @Test
    public void test3() {

        given().
                accept(ContentType.JSON).
                queryParam("region", "Nepal").
                queryParam("gender", "male").
        when().
                get().
        then().
                assertThat().
                    statusCode(200).
                    contentType("application/json; charset=utf-8").
                    body("gender",is("male")).
                    body("region",is("Nepal"));
    }
    @Test
    public void test4() {

        given().
                accept(ContentType.JSON).
                queryParam("gender", "malle").
        when().
                get().
        then().
                assertThat().
                    statusCode(400).
                    statusLine(containsString("Bad Request")).
                    body("error",is("Invalid gender"));
    }

    @Test
    public void test5() {

        given().
                accept(ContentType.JSON).
                queryParam("region", "noPlace").
        when().
                get().
        then().
                assertThat().
                    statusCode(400).
                    statusLine(containsString("Bad Request")).
                    body("error",is("Region or language not found"));
    }

    @Test
    public void test6() {

        JsonPath json = given().
                                accept(ContentType.JSON).
                                queryParam("region", "Nepal").
                                queryParam("amount", 25).
                        when().
                                get().
                        then().
                                assertThat().
                                    statusCode(200).
                                    contentType("application/json; charset=utf-8").extract().jsonPath();

        List<Map<String,String>> people = json.getList("");
        Set<String> fullnames =new HashSet<>();

        for (Map entries:people ) {

            String fullName = entries.get("name") +" "+ entries.get("surname");
            fullnames.add(fullName);
        }
        Assertions.assertEquals(fullnames.size() ,people.size(), "there is same full name in list");

    }

    @Test
    public void test7() {
       given().
               accept(ContentType.JSON).
               queryParam("region", "Nepal").
               queryParam("gender", "male").
               queryParam("amount", 25).
       when().
               get().
       then().
               assertThat().
                   statusCode(200).
                   contentType("application/json; charset=utf-8").
                   body("region",everyItem(is("Nepal"))).
                   body("gender",everyItem(is("male")));

    }
    /*@SerializedName("id")// "id"
    private int spartanId;
    private String name;// "name"
    private String gender;//"gender"
    private long phone;//"phone"*/
    public static void main(String[] args)  {

        List<Map<String,String>> people  = given().baseUri("https://uinames.com/api/").
                accept(ContentType.JSON).
                queryParam("region", "Nepal").
                queryParam("amount", 25).
                when().
                get().
                thenReturn().jsonPath().getList("");

        System.out.println("is map get people :" +( people.size() > 24));

        int sparatanId = 2000;
        for (Map entries:people ) {

            String name = entries.get("name") +"";
            String gender = entries.get("gender")+"";
            long phone = sparatanId + 2345568000l;
            sparatanId++;
            Spartan spartan = new Spartan(name,gender,phone);
            System.out.println("the  knowledge of created spartan: "+spartan.getName()+" "+spartan.getPhone()+" "+spartan.getGender());
            Response response = given().
                    contentType(ContentType.JSON).
                    body(spartan).
                    when().
                    post("/spartans");
            assertEquals(201, response.getStatusCode(), "Status code is wrong!");
            assertEquals("application/json", response.getContentType(), "Content type is invalid!");
            assertEquals(response.jsonPath().getString("success"), "A Spartan is Born!");


        }

    }


}
