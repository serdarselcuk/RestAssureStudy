package com.automation.tests.day3;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
public class warmUp {

    public static void main(String[] args) {
         baseURI= "http://api.aladhan.com/v1";

         String month = "02";
         String year = "2020";
         String country = "US";
         String city = "WI";

         JsonPath js =given().accept(ContentType.JSON).
//                 basePath(basePath+"/calendarByCity").
                 pathParam("mappingType","calendarByCity").
                 queryParam("city",city).
                 queryParam("country",country).
                 queryParam("method","2").
                 queryParam("month",month).
                 queryParam("year",year).
         when().get("/{mappingType}").
         thenReturn().jsonPath();

        List<?>timing = js.getList("data.timings");

    }

}
