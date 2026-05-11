package com.api.test;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static org.hamcrest.Matcher.*;

import static com.api.constants.Roles.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CountAPITest {
	
	@Test
	public void verifyCountAPIResponse() throws IOException {
	   given()
	   .baseUri(ConfigManager.getProperty("BASE_URI"))
	   .and()
	   .header("Authorization", AuthTokenProvider.getToken(FD))
	   .log().uri()
	   .log().method()
	   .log().headers()
	   .when()
	   .get("/dashboard/count")
	   .then()
	   .log().all()
	   .statusCode(200)
	   .body("message",equalTo("Success"))
	   .time(lessThan(1000L))
	   .body("data",notNullValue())
	   .body("data.size()",equalTo(3))
	   .body("data.count", everyItem(greaterThanOrEqualTo(0)))
	   .body("data.label",everyItem(not(blankOrNullString())))
	   .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"))
	   .body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"));
	   
	}
	@Test
	public void countAPITest_MissingAuthToken() throws IOException {
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
		
	}

}
