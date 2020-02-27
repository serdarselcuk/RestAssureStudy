package com.automation.tests.homework;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class Examples {


    @Test
    public void test() {
        // https://www.metaweather.com/api/location/search/?query=New

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "John");
        map.put("lname", "doe");

        Response response = given().                                                                        // prepare the request, add header, add params
                                   queryParams(map).
                             when().
                                    get("http://api.cybertektraining.com/student/all/");// specify the method, url

        response.then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON);












        response.then().body("rates.GPD", equalTo("1000"));



    }
}
