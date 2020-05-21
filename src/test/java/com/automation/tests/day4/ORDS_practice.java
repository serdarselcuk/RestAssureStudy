package com.automation.tests.day4;

import com.automation.pojos.Employees;
import org.junit.jupiter.api.BeforeAll;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ORDS_practice {

    @BeforeAll
    public static void startUp(){
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }
    @Test
    public void test(){
        List<Map<String, String>>list = given().header("ContendType","application/json").get("/employees").jsonPath().getList("items");

        System.out.println("As map in  List = " + list);


    }
    @Test
    public void test2(){

        Map<String,String>map = given().accept(ContentType.JSON).
                get("/employees").thenReturn().jsonPath().getMap("items[0]");
        System.out.println(map);

    }

    @Test
    public void test3(){
        JsonPath js = given().accept(ContentType.JSON).
                get("/employees").thenReturn().jsonPath();

        Employees employee = js.getObject("items",Employees.class);
        System.out.println(employee.getFirst_name());
    }
}
