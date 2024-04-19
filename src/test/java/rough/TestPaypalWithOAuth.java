package rough;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import org.json.JSONObject;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestPaypalWithOAuth {
    
    static String client_id = "";
    static String secret_key = "";
    
    static String access_token, orderId;
    
    String jsonCreateOrder = "{\n"
    	+ "  \"intent\": \"CAPTURE\",\n"
    	+ "  \"purchase_units\": [\n"
    	+ "    {\n"
    	+ "      \"reference_id\": \"d9f80740-38f0-11e8-b467-0ed5f89f718b\",\n"
    	+ "      \"amount\": {\n"
    	+ "        \"currency_code\": \"USD\",\n"
    	+ "        \"value\": \"100.00\"\n"
    	+ "      }\n"
    	+ "    }\n"
    	+ "  ]\n"
    	+ "}";
    
    @Test
    public void getAuthKey() {
	
	RestAssured.baseURI = "https://api-m.sandbox.paypal.com/";
	
	Response response = given().
			    param("grant_type", "client_credentials").
			    auth().
			    preemptive().
			    basic(client_id, secret_key).
			    post("v1/oauth2/token");
	
	response.prettyPrint();
	
	JSONObject objJSONObject = new JSONObject(response.asString());
	access_token = objJSONObject.getString("access_token");
	System.out.println("The access_token is: " + access_token);
    }
    
    @Test(dependsOnMethods = "getAuthKey")
    public void createOrder() {
	
	RestAssured.baseURI = "https://api-m.sandbox.paypal.com/";
	
	Response response = given().
			    contentType(ContentType.JSON).
			    auth().
			    oauth2(access_token).
			    body(jsonCreateOrder).
			    post("v2/checkout/orders");
	
	response.prettyPrint();
	
	JSONObject objJSONObject = new JSONObject(response.asString());
	orderId = objJSONObject.getString("id");
	System.out.println("The orderId is: " + orderId);
    }
    
    @Test(dependsOnMethods = "createOrder")
    public void getOrderDetails() {
	
	RestAssured.baseURI = "https://api-m.sandbox.paypal.com/";
	
	Response response = given().
			    contentType(ContentType.JSON).
			    auth().
			    oauth2(access_token).
			    get("/v2/checkout/orders/" + orderId);
	
	response.prettyPrint();
	
	JSONObject objJSONObject = new JSONObject(response.asString());
	System.out.println("The intent value from getOrderDetails is: " + objJSONObject.getString("intent"));
    }
}
