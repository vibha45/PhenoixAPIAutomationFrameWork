package com.api.test;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import  com.api.constants.Roles;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {
	@Test
	public void verifyMasterAPI() throws IOException
	{
	     given()//pre-condition
	     .baseUri(ConfigManager.getProperty("BASE_URI"))
	     .headers("Authorization",AuthTokenProvider.getToken(Roles.FD))
	     .and()
	     .contentType("")//empty content type but it's a bad request
	     .log().uri()
	     .log().method()
	     .log().headers()
	     .when()//actions
	     .post("master")//default content-type application/url-formencoded
	     .then()//validatable response
	     .log().all()
	     .statusCode(200)
	     .body("message", equalTo("Success"))
			.time(lessThan(1000L))
			.body("data",notNullValue())
			.body("data",hasKey("mst_oem"))
			.body("data",hasKey("mst_model"))
			.body("data", hasKey("mst_warrenty_status"))
			.body("data",hasKey("mst_platform"))
			.body("data.mst_oem.size()", greaterThan(0))
			.body("data.mst_model.size()", equalTo(3))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema-FD.json"));
	   
	}
	@Test
  public void invalidTokenMasterAPITest() throws IOException
  {
	 given().baseUri(ConfigManager.getProperty("BASE_URI")).header("Authorization"," ").and().contentType("").log().all()
	 .when().post("master").then().log().all().and().statusCode(401);
	 
  }
}
