package testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import apis.APICreateCustomer;
import setup.BaseTest;
import utilities.DataUtil;
import utilities.TestUtil;

public class TestCreateCustomer extends BaseTest {

    //@Test(dataProviderClass = DataUtil.class, dataProvider = "getData1")
    public void test01_ValidCreateCustomerAPI(String name, String email, String description) throws IOException {
	response = APICreateCustomer.sendPostRequestToCreateCustomerAPIWithValidAuthKey(name, email, description);

	response.prettyPrint();
	System.out.println(response.statusCode());

	Assert.assertEquals(response.statusCode(), 200);

    }

    @Test(dataProviderClass = DataUtil.class, dataProvider = "getDataUsingHashTable")
    public void test01_ValidCreateCustomerAPI(Hashtable<String, String> table) throws IOException {
	response = APICreateCustomer.sendPostRequestToCreateCustomerAPIWithValidAuthKey(table);

	response.prettyPrint();
	System.out.println(response.statusCode());

	Assert.assertEquals(response.statusCode(), 200);

	Assert.assertTrue(TestUtil.jsonHasKey(response.asString(), "id"), "Test case failed as the id is not present in the response");

	Assert.assertEquals(TestUtil.getJSONKeyValue(response.asString(), "object"), "customer", "Test case failed");

    }

    @Test(dataProviderClass = DataUtil.class, dataProvider = "getData1")
    public void test02_InvalidCreateCustomerAPI(String name, String email, String description) {
	response = APICreateCustomer.sendPostRequestToCreateCustomerAPIWithInvalidAuthKey(name, email, description);

	response.prettyPrint();
	System.out.println(response.statusCode());

	Assert.assertEquals(response.statusCode(), 200);

    }

}
