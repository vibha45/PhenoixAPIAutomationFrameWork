package com.api.utils;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import static com.api.constants.Roles.*;

import com.api.constants.Roles;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	//I want to make login api request and extract the token
	private AuthTokenProvider()
	{
		
	}
	
	public static String getToken(Roles role) throws IOException
	{
		UserCredentials userCredential;
		if(role==FD)
		{
			userCredential = new UserCredentials("iamfd", "password");
		}
		else if( role==SUP)
		{
			userCredential = new UserCredentials("iamsup", "password");
		}
		else if( role==ENG)
		{
			userCredential = new UserCredentials("iameng", "password");
		}
		else if( role==QC)
		{
			userCredential = new UserCredentials("iamqc", "password");
		}
		else
		{
			System.out.println("Please enter the correct Role!!"+role);
		}
		
		String token = given().baseUri(ConfigManager.getProperty("BASE_URI")).contentType(ContentType.JSON)
				.body(new UserCredentials("iamfd", "password")).when().post("login").then().log().ifValidationFails()
				.statusCode(200)
				 .body("message", equalTo("Success"))
				.extract().body()
				.jsonPath().getString("data.token");

		System.out.println("------------------------------------");
		System.out.println(token);
		return token;
		 
		 
	}

}
