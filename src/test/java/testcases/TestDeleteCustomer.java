package testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import apis.APIDeleteCustomer;
import setup.BaseTest;
import utilities.DataUtil;
import utilities.TestUtil;

public class TestDeleteCustomer extends BaseTest {

    @Test(dataProviderClass = DataUtil.class, dataProvider = "getData1")
    public void deleteCustomerAPI(String id) throws IOException {
	response = APIDeleteCustomer.sendDeleteRequestToDeleteCustomerAPIWithValidId(id);

	response.prettyPrint();
	System.out.println(response.statusCode());
	Assert.assertEquals(response.statusCode(), 200);

	Assert.assertTrue(TestUtil.jsonHasKey(response.asString(), "id"), "Test case failed as the id is not present in the response");

	Assert.assertEquals(TestUtil.getJSONKeyValue(response.asString(), "id"), id, "Test case failed as the id is not same");

	Assert.assertEquals(TestUtil.getJSONKeyValue(response.asString(), "deleted"), "true", "Test case failed as deleted key value is not true");

    }

}
