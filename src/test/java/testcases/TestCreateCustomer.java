package testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import apis.APICreateCustomer;
import setup.BaseTest;
import utilities.DataUtil;

public class TestCreateCustomer extends BaseTest {

	@Test(dataProviderClass = DataUtil.class, dataProvider = "getData")
	public void validCreateCustomerAPI(String name, String email, String description) throws IOException {
		response = APICreateCustomer.sendPostRequestToCreateCustomerAPIWithValidAuthKey(name, email, description);

		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 200);

	}

	@Test(dataProviderClass = DataUtil.class, dataProvider = "getData")
	public void invalidCreateCustomerAPI(String name, String email, String description) {
		response = APICreateCustomer.sendPostRequestToCreateCustomerAPIWithInvalidAuthKey(name, email, description);

		response.prettyPrint();
		System.out.println(response.statusCode());

		Assert.assertEquals(response.statusCode(), 200);

	}

}
