package apis;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import setup.BaseTest;

public class APICreateCustomer extends BaseTest {

    public static Response sendPostRequestToCreateCustomerAPIWithValidAuthKey(String name, String email,
	    String description) {
	response = given().auth().basic(config.getProperty("validSecretKey"), "").formParam("email", email)
		.formParam("description", description).post(config.getProperty("customerAPIEndPoint"));

	return response;
    }

    public static Response sendPostRequestToCreateCustomerAPIWithInvalidAuthKey(String name, String email,
	    String description) {
	response = given().auth().basic(config.getProperty("invalidSecretKey"), "").formParam("email", email)
		.formParam("description", description).post(config.getProperty("customerAPIEndPoint"));

	return response;
    }

}
