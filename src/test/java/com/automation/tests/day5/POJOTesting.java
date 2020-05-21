package com.automation.tests.day5;


import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.pojos.Spartan;
import com.automation.pojos.Student;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import static io.restassured.RestAssured.*;

public class POJOTesting {

    private Object spartan;


    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Get job info from JSON and convert it into POJO")
    public void test1() {
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/jobs");

        JsonPath jsonPath = response.jsonPath();
        //this is deserialization
        // from JSON to POJO (java object)
        Job job = jsonPath.getObject("items[0]", Job.class);//Job.class type of POJO that you gonna create from JSON

        System.out.println(job);

        System.out.println("Job id: " + job.getJobId());
    }

    @Test
    @DisplayName("Convert from POJO to JSON")
    public void test2() {
        Job sdet = new Job("SDET", " Software Development Engineer in Test", 5000, 20000);

        Gson gson = new Gson();
        String json = gson.toJson(sdet); // convert POJO to JSON: serialization

        System.out.println("JSON file" + json);
        System.out.println("From toString(): " + sdet);
    }

    @Test
    @DisplayName("Convert JSON into collection of POJO's")
    public void test3() {
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/jobs");

        JsonPath jsonPath = response.jsonPath();
        List<Job> jobs = jsonPath.getList("items", Job.class);


        for (Job job : jobs) {
            System.out.println(job.getJob_title());
        }

        for (Job job : jobs) {
            System.out.println(job);
        }
    }

    @Test
    @DisplayName("Convert from JSON to Location POJO")
    public void test4() {
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/locations/{location_id}", 2500);
        Location location = response.jsonPath().getObject("", Location.class);

        System.out.println(location);


        Response response2 = given().
                accept(ContentType.JSON).
                when().
                get("/locations");

        List<Location> locations = response2.jsonPath().getList("items", Location.class);

        for (Location l : locations) {
            System.out.println(l);
        }
    }

    Gson gson = new Gson();;
    BufferedReader br;
    Spartan spa;
    @Test
    @DisplayName("spartan red from json file")
    public void test5() throws IOException {

        try {
            br = new BufferedReader(
                    new FileReader(System.getProperty("user.dir")+"/spartan.json"));

            spa = gson.fromJson(br,Spartan.class);

            System.out.println(spa);
        } catch (IOException e) {
            System.out.println("Failed to load properties file!");
            e.printStackTrace();
        }

    }
    @Test
    @DisplayName("Spartan writen to house.json")
    public void test6(){
        String toHouse = gson.toJson(spa);
        try {
            //write converted json data to a file named "CountryGSON.json"
            FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/house.json");
            writer.write(toHouse);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Test
    @DisplayName("get multiple objects")
    public void test7(){
        Student student =null;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/student.json"));
            Gson gson = new Gson();
            student = gson.fromJson(bf,Student.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(student.toString());
    }
}
