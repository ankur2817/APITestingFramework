package apis;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import setup.BaseTest;

public class APIDeleteCustomer extends BaseTest {

    public static Response sendDeleteRequestToDeleteCustomerAPIWithValidId(String id) {
	response = given().
		auth().
		basic(config.getProperty("validSecretKey"), "").
		delete(config.getProperty("customerAPIEndPoint") + "/" + id);

	return response;
    }
}
