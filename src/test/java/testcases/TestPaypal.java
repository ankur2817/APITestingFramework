package testcases;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.PaypalCreateOrders;
import pojo.PurchaseUnits;

public class TestPaypal {

    static String access_Token;
    static String client_id = "";
    static String secret_key = "";

    static String baseURI = "https://api-m.sandbox.paypal.com";
    
    static String orderId;

    @Test
    public void getAuthKey() {
	RestAssured.baseURI = baseURI;

	Response response = given().param("grant_type", "client_credentials").auth().preemptive()
		.basic(client_id, secret_key).post("/v1/oauth2/token");

	response.prettyPrint();

	access_Token = response.jsonPath().get("access_token").toString();
	System.out.println(access_Token);
    }

    @Test(dependsOnMethods = "getAuthKey")
    public void createOrder() {
	ArrayList<PurchaseUnits> list = new ArrayList<PurchaseUnits>();
	list.add(new PurchaseUnits("USD", "500.00"));

	PaypalCreateOrders orders = new PaypalCreateOrders("CAPTURE", list);

	RestAssured.baseURI = baseURI;

	Response response = given().contentType(ContentType.JSON).auth().oauth2(access_Token).body(orders)
		.post("v2/checkout/orders");
	response.prettyPrint();
	
	JSONObject objJSONObject = new JSONObject(response.asString());
	orderId = objJSONObject.getString("id");

	Assert.assertEquals(response.jsonPath().get("status").toString(), "CREATED");

    }
    
    @Test(dependsOnMethods = "createOrder")
    public void getOrderDetails() {
	
	RestAssured.baseURI = "https://api-m.sandbox.paypal.com/";
	
	Response response = given().
			    contentType(ContentType.JSON).
			    auth().
			    oauth2(access_Token).
			    get("/v2/checkout/orders/" + orderId);
	
	response.prettyPrint();
	
	JSONObject objJSONObject = new JSONObject(response.asString());
	System.out.println("The intent value from getOrderDetails is: " + objJSONObject.getString("intent"));
    }

}
