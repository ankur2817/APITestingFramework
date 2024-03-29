package rough;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import org.json.JSONObject;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.Users;

import java.io.File;


public class TestRequests {

    public static void main(String[] args) {

	//Get the list of all customers
	Response response = given()
		.header("Content-Type", "application/json")
		.param("limit", 3)
		.auth()
		.basic("sk_test_51NbkkVSBIFrUJ0aDPYQTkz3Z2OzQkjROom3r7MrT40ld2dd7XM3UE11xiBFZ9p60OF71Eb2M1ebm0vIsFSg8I9F100RZMCeiGb", "")
		.get("https://api.stripe.com/v1/customers");
	response.prettyPrint();
	System.out.println("Status code is => " + response.statusCode());
	System.out.println("Response time (milliseconds) is => " + response.time());

	System.out.println("=======================================================");

	//Get the data of single/one customer
	Response response1 = given()
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer sk_test_51NbkkVSBIFrUJ0aDPYQTkz3Z2OzQkjROom3r7MrT40ld2dd7XM3UE11xiBFZ9p60OF71Eb2M1ebm0vIsFSg8I9F100RZMCeiGb")
		.get("https://api.stripe.com/v1/customers/cus_Pk4LIfr4FAvvT1");
	response1.prettyPrint();
	System.out.println("Status code is => " + response1.statusCode());
	System.out.println("Response time (milliseconds) is => " + response1.time());

	System.out.println("=======================================================");

	//Create a new customer using formParam
	Response response2 = given()
		.header("Content-Type", "application/x-www-form-urlencoded")
		.header("Authorization", "Bearer sk_test_51NbkkVSBIFrUJ0aDPYQTkz3Z2OzQkjROom3r7MrT40ld2dd7XM3UE11xiBFZ9p60OF71Eb2M1ebm0vIsFSg8I9F100RZMCeiGb")
		.formParam("name", "Anika Gupta")
		.formParam("email", "abc2817@gmail.com")
		.formParam("description", "Creating a new customer")
		.formParam("address[line1]", "A008")
		.formParam("preferred_locales[0]", "Hindi")
		.post("https://api.stripe.com/v1/customers");
	response2.prettyPrint();
	System.out.println("Status code is => " + response2.statusCode());
	System.out.println("Response time (milliseconds) is => " + response2.time());
	
	JsonPath objJsonPath = response2.jsonPath();
	System.out.println(objJsonPath.getString("preferred_locales[0]"));
	if(objJsonPath.getString("preferred_locales[0]").equalsIgnoreCase("hindi"))
	    System.out.println("!!!!!!!!!!!!! Balle Balle !!!!!!!!!!!!!");
	if(objJsonPath.getString("address.line1").equalsIgnoreCase("A008"))
	    System.out.println("!!!!!!!!!!!!! Balle Balle !!!!!!!!!!!!!");
	


	System.out.println("=======================================================");

	//Create a new customer using json body using HashMap
	HashMap<String, String> hm = new HashMap<String, String>();
	hm.put("email", "eve.holt@reqres.in");
	hm.put("password", "pistol");
	Response response3 = given()
		.header("Content-Type", "application/json")
		.body(hm)
		.post("https://reqres.in/api/register");
	response3.prettyPrint();
	System.out.println("Status code is => " + response3.statusCode());
	System.out.println("Response time (milliseconds) is => " + response3.time());

	System.out.println("=======================================================");

	//Create a new customer using json body from a file
	Response response4 = given()
		.header("Content-Type", "application/json")
		.body(new File("./sampleJSON/request/users.json"))
		.post("https://reqres.in/api/register");
	response4.prettyPrint();
	System.out.println("Status code is => " + response4.getStatusCode());
	System.out.println("Response time (milliseconds) is => " + response4.time());

	System.out.println("=======================================================");

	//Create a new customer using json body via JSONObject class from json library
	JSONObject objJSONObject = new JSONObject();
	objJSONObject.put("email", "eve.holt@reqres.in");
	objJSONObject.put("password", "pistol");

	Response response5 = given()
		.header("Content-Type", "application/json")
		.body(objJSONObject.toString())
		.post("https://reqres.in/api/register");
	response5.prettyPrint();
	System.out.println("Status code is => " + response5.getStatusCode());
	System.out.println("Response time (milliseconds) is => " + response5.time());

	System.out.println("=======================================================");

	//Create a new customer using json body via pojo class
	Users objUsers = new Users("eve.holt@reqres.in", "pistol");

	Response response6 = given()
		.header("Content-Type", "application/json")
		.body(objUsers)
		.post("https://reqres.in/api/register");
	response6.prettyPrint();
	System.out.println("Status code is => " + response6.getStatusCode());
	System.out.println("Response time (milliseconds) is => " + response6.time());
	
	System.out.println("=======================================================");

    }

}
