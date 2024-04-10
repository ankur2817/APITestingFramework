package testcases;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.PaypalCreateOrders;
import pojo.PurchaseUnits;

public class TestPaypal {

    static String access_Token;
    static String client_Id = "";
    static String scretKey = "";

    static String baseURI = "https://api-m.sandbox.paypal.com";

    @Test
    public void getAuthKey() {
	RestAssured.baseURI = baseURI;

	Response response = given().param("grant_type", "client_credentials").auth().preemptive()
		.basic(client_Id, scretKey).post("/v1/oauth2/token");

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

	Assert.assertEquals(response.jsonPath().get("status").toString(), "CREATED");

    }

}
