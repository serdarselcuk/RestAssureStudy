package com.automation.tests.day4;

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
        baseURI = ConfigurationReader.getProperty("meta.weather.uri");
    }
    @Test
    public void test(){

    }
}
