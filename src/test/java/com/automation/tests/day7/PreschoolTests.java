package com.automation.tests.day7;

import static io.restassured.RestAssured.*;

import com.automation.pojos.Student;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PreschoolTests {

    @BeforeAll
    public static void startUp(){
        baseURI = ConfigurationReader.getProperty("school.uri");
    }
    @Test
    @DisplayName("get student 2633 and convert payload POJO")
    public void test1(){
        int idNum = 2633;
        Student student =
                    given().
                            accept(ContentType.JSON).
                            pathParam("id",idNum).
                    when().
                            get("student/{id}").prettyPeek().
                    thenReturn().
                            jsonPath().
                            getObject("students[0]", Student.class);


    }

}
