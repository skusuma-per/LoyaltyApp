package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.restClientAPI;
import com.qa.config.TestUtil;


/* 
 1. GET Reward Balance for one user based  on the userId
 */


public class Test3_get_RewardBalance extends TestBase {
	
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
	public void get_RewardsforAUser() throws ClientProtocolException, IOException
	{
		rca = new restClientAPI();
		HashMap<String, String> headerMap = new HashMap<String, String>();
				headerMap.put("content-type", "application/json");
		headerMap.put("userId", "1234");
		headerMap.put("userName", "kasasa");
		closablehttpresponse = rca.get(url, headerMap);
		
		
		//Assertions  - statuscode, headers and jsonresponse
		
		int statuscode = closablehttpresponse.getStatusLine().getStatusCode();
		System.out.println("StatusCode = "+statuscode);
		Assert.assertEquals(statuscode,response_code_200,"Statuscode is not 200");
		
		Header[] headersArray =closablehttpresponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue())	;	
			}
		System.out.println("Headers Array"+allHeaders);
		
		
		String responsString = EntityUtils.toString(closablehttpresponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsString);
		System.out.println("Response Json from API call ---> "+responseJson);
		
		//Assertions for Json Array
		String userName = TestUtil.getValueByJPath(responseJson, "/data[0]/userName");
		String userId = TestUtil.getValueByJPath(responseJson, "/data[0]/userId");
		String rewardBalance = TestUtil.getValueByJPath(responseJson, "/data[0]/rewardBalance");
		System.out.println("username " +userName);
		System.out.println("userId " +userId);
		System.out.println("rewardBalance " +rewardBalance);

		//DB Validation
		
		String s = "select * from rewardpoints";
		
	}
	
	/* Alternative Test
	
	@Test
	public void get_RewardsforAUser() 
		{
		String userId="1234";
			
			given()
				.param("userId", 1234)
			.when()
				.get("url")
			.then()
				.statusCode(response_code_200)
				.assertThat().body("userId", equalTo(getUserId()))
				.assertThat().body("rewardsBalance", equalTo(getRewardBalance()))
							
		}
		
		*/

	
	
}
