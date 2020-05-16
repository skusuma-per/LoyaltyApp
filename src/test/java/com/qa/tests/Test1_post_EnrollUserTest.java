package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.restClientAPI;
import com.qa.data.Users;

public class Test1_post_EnrollUserTest extends TestBase {
	
	TestBase tb;
	String serviceUrl;
	String apiUrl;
	String url;
	restClientAPI rca;
	CloseableHttpResponse closablehttpresponse;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException
		{
			tb = new TestBase();
			serviceUrl = prop.getProperty("URL");
			apiUrl=prop.getProperty("serviceURL");
			url= serviceUrl + apiUrl;
						
		}
	
	@Test
	public void enrollUser() throws JsonGenerationException, JsonMappingException, IOException{
		
		rca = new restClientAPI();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("content-type", "application/json");
				
		//jackson Java API:
		ObjectMapper mapper = new ObjectMapper();
		Users u = new Users("kasasa","1234","200","20000");
		
		//Convert javaobject to Json File
		mapper.writeValue(new File("C:/Users/Jeremy Brua/eclipse-workspace/KasasaLoyaltyApp/src/main/java/com/qa/data/users.json"),u);
		
		//Convert json to jsonString
		String userJsonString = mapper.writeValueAsString(u);
		System.out.println(userJsonString);
		closablehttpresponse = rca.post(url, userJsonString, headerMap);
				
		
		//Assertions  - statuscode, headers and jsonstring
		
		int statuscode = closablehttpresponse.getStatusLine().getStatusCode();
		System.out.println("StatusCode = "+statuscode);
		Assert.assertEquals(statuscode,response_code_201,"Statuscode is not 201");
				
		String responsString = EntityUtils.toString(closablehttpresponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsString);
		System.out.println("Response Json from API call ---> "+responseJson);
		
		//json to javaobj
		Users uresobj = mapper.readValue(responsString, Users.class);
		System.out.println("Users Result object"+uresobj);
		
		//assertion
		Assert.assertTrue(u.getUserName().equals(uresobj.getUserName()));
		System.out.println(u.getUserName().equals(uresobj.getUserName()));
		Assert.assertTrue(u.getUserId().equals(uresobj.getUserId()));
		System.out.println(u.getUserId().equals(uresobj.getUserId()));
		Assert.assertTrue(u.getRewardPoints().equals(uresobj.getRewardPoints()));
		System.out.println(u.getRewardPoints().equals(uresobj.getRewardPoints()));
		Assert.assertTrue(u.getRewardBalance().equals(uresobj.getRewardBalance()));
		System.out.println(u.getRewardBalance().equals(uresobj.getRewardBalance()));
		
		//DB Validation
		
		
		
	}
	

}
