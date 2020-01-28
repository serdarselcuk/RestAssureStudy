package com.automation.tests.day5;


import com.automation.pojos.Job;
import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOTesting {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Get job info from JSON and convert it into POJO")
    public void test1(){
        Response response = given().
                                accept(ContentType.JSON).
                            when().
                                get("/jobs");

        JsonPath jsonPath = response.jsonPath();
        //this is deserialization
        // from JSON to POJO (java object)
        Job job = jsonPath.getObject("items[0]", Job.class);//Job.class type of POJO that you gonna create from JSON

        System.out.println(job);

        System.out.println("Job id: "+job.getJobId());

        //comeback at 3:08
    }
}