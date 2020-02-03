package com.automation.tests.assignment;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.google.common.collect.Ordering;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GitHubAPI_Test {
    @BeforeAll
    public static void startUp() {
        baseURI = "https://api.github.com";
    }

    @Test
    @DisplayName("Verify organization information")
    public void test1() {

        given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
            when().
                get("/orgs/{org}").
            then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                body("login", is("cucumber")).
                body("name", is("Cucumber")).
                body("id", is(320565));
    }

    @Test
    @DisplayName("Verify Error Message")
    public void test2() {
        given().
                accept(ContentType.XML).
                pathParam("org", "cucumber").
            when().
                get("/orgs/{org}").
            then().
                assertThat().
                statusCode(415).
                contentType("application/JSON; charset=utf-8").
                statusLine(containsString("Unsupported Media Type"));

    }

    @Test
    @DisplayName("Verify Number of Repositories")
    public void test3() {
        given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
                queryParam("per_page", 100).
                queryParam("page", 1).
            when().
                get("/orgs/{org}/repos").
            then().
                assertThat().
                body("", hasSize(10));

    }

    @Test
    @DisplayName("Verify id information")
    public void test4() {
        given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
                queryParam("per_page", 10).
                queryParam("page", 1).
            when().
                get("/orgs/{org}/repos").
            then().
                assertThat().
                body("owner.id", everyItem(is(320565)));

    }

    @Test
    @DisplayName("Verifyrepository id information")
    public void test5() {

        Response response = given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
            when().
                get("/orgs/{org}/repos");

        response.then().assertThat().body("id",hasSize(new HashSet<>(response.jsonPath().get("id")).size()))
                                    .body("node_id",hasSize(new HashSet<>(response.jsonPath().get("node_id")).size()));
    // if there is an "id" which is not unique, set will not accept and size will be changed. So it won't be equal to parameter size
    }

    @Test
    @DisplayName("Verify repository owner information")
    public void test6() {
        int ownerId = 320565;
        given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
                queryParam("id", ownerId).
            when().
                get("/orgs/{org}/repos").
            then().
                assertThat().
                body("owner.id",everyItem(is(ownerId)));

    }

    @Test
    @DisplayName("Verify ordered fullName")
    public void test7() {
        List<String> list = Collections.singletonList(given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
                queryParam("sort", "full_name").
                when().
                get("/orgs/{org}/repos").jsonPath().getString("name"));

        Assertions.assertTrue( Ordering.natural().isOrdered(list));

    }


    @Test
    @DisplayName("Verify ordered by name sort")
    public void test8() {
        List<String> list = Collections.singletonList(given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
                queryParam("sort", "full_name").
                queryParam("direction", "desc").
                when().
                get("/orgs/{org}/repos").jsonPath().getString("name"));

        Assertions.assertTrue(Ordering.natural().reverse().isOrdered(list));

    }
    @Test
    @DisplayName("Verify default sort")
    public void test9() {
        List<String> list = Collections.singletonList(given().
                accept(ContentType.JSON).
                pathParam("org", "cucumber").
                when().
                get("/orgs/{org}/repos").jsonPath().getString("created_at"));

        Assertions.assertTrue(  Ordering.natural().reverse().isOrdered(list));

    }
}

