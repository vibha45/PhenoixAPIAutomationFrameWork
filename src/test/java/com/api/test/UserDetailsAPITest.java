package com.api.test;

import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.security.AuthProvider;

import org.testng.annotations.Test;

import static com.api.constants.Roles.*;

import static com.api.utils.ConfigManagerOLD.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;
import static com.api.utils.AuthTokenProvider.*;

public class UserDetailsAPITest {
	 
	
	@Test
	public void userDetailsAPITest() throws IOException
	{
		Header authHeader =new Header("Authorization",getToken(ENG));
		
		given()
		 .baseUri(getProperty("BASE_URI"))
		 .and()
		 .header(authHeader)
		 .and()
		 .contentType(ContentType.JSON)
		 .log().uri()
		 .log().method()
		 .log().headers()
		 .log().body()
		 .when()
		 .get("userdetails")
		 .then()
		 .log().all()
		 .statusCode(200)
		 .time(lessThan(1000L))
		 .and()
		 .body("message",equalTo("Success"))
		 .and()
		 .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
		  
		 
		 
	}

}
