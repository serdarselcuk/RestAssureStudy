package com.automation.tests.assignment;

import groovy.json.JsonOutput;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
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
    @Test
    public void test8() {

        given().
                accept(ContentType.JSON).
                queryParam("region", "Nepal").
                queryParam("amount", 25).
                when().
                get().
                then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                body("",hasSize(25));


    }
    @Test
    public void test9() {

        JsonPath json = given().
                accept(ContentType.JSON).
                queryParam("amount", 25).
                when().
                get().prettyPeek().
                then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").extract().jsonPath();

        List<Map<String, String>> people = json.getList("");

        try {
            FileOutputStream file = new FileOutputStream(System.getProperty("user.dir"));
            JsonOutput js = new JsonOutput();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Map entries : people) {

            String fullName = entries.get("name") + " " + entries.get("surname");

        }

    }
}
