package utilities;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;

import setup.BaseTest;

public class DataUtil extends BaseTest {
    @DataProvider(name = "getData", parallel = true)
    public Object[][] getData(Method m) {
	String sheetName = m.getName();

	int rows = excel.getRowCount(sheetName);
	int cols = excel.getColumnCount(sheetName);

	Object[][] data = new Object[rows - 1][cols];

	for (int rowNum = 2; rowNum <= rows; rowNum++) {
	    for (int colNum = 0; colNum < cols; colNum++) {
		data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
	    }
	}

	return data;
    }

    @DataProvider(name = "getData1", parallel = true)
    public Object[][] getData1(Method m) {

	String sheetName = "testdata";
	String testCaseName = m.getName();

	int totalRowsInSheet = excel.getRowCount(sheetName);
	System.out.println("Total number of rows in the test data sheet: " + totalRowsInSheet);

	// Find the test case start row number
	int testCaseRowNum = 1;
	for (testCaseRowNum = 1; testCaseRowNum < totalRowsInSheet; testCaseRowNum++) {
	    String testCaseNameFromExcel = excel.getCellData(sheetName, 0, testCaseRowNum);

	    if (testCaseNameFromExcel.equalsIgnoreCase(testCaseName)) {
		break;
	    }
	}

	System.out.println("Test case starts from row number: " + testCaseRowNum);

	// Check total rows in test case
	int dataStartRowNum = testCaseRowNum + 2;
	int testRows = 0;
	while (!excel.getCellData(sheetName, 0, dataStartRowNum + testRows).equals("")) {
	    testRows++;
	}
	System.out.println("Total rows of data are: " + testRows);

	// Check total columns in test case
	int testColumns = 0;
	while (!excel.getCellData(sheetName, testColumns, testCaseRowNum + 1).equals("")) {
	    testColumns++;
	}
	System.out.println("Total columns of data are: " + testColumns);

	Object[][] data = new Object[testRows][testColumns];

	for (int rowNum = dataStartRowNum; rowNum < dataStartRowNum + testRows; rowNum++) {
	    for (int colNum = 0; colNum < testColumns; colNum++) {
		data[rowNum - dataStartRowNum][colNum] = excel.getCellData(sheetName, colNum, rowNum);
	    }
	}

	return data;

    }

    @DataProvider(name = "getDataUsingHashTable", parallel = true)
    public Object[][] getDataUsingHashTable(Method m) {

	String sheetName = "testdata";
	String testCaseName = m.getName();

	int totalRowsInSheet = excel.getRowCount(sheetName);
	System.out.println("Total number of rows in the test data sheet: " + totalRowsInSheet);

	// Find the test case start row number
	int testCaseRowNum = 1;
	for (testCaseRowNum = 1; testCaseRowNum < totalRowsInSheet; testCaseRowNum++) {
	    String testCaseNameFromExcel = excel.getCellData(sheetName, 0, testCaseRowNum);

	    if (testCaseNameFromExcel.equalsIgnoreCase(testCaseName)) {
		break;
	    }
	}

	System.out.println("Test case starts from row number: " + testCaseRowNum);

	// Check total rows in test case
	int dataStartRowNum = testCaseRowNum + 2;
	int testRows = 0;
	while (!excel.getCellData(sheetName, 0, dataStartRowNum + testRows).equals("")) {
	    testRows++;
	}
	System.out.println("Total rows of data are: " + testRows);

	// Check total columns in test case
	int testColumns = 0;
	while (!excel.getCellData(sheetName, testColumns, testCaseRowNum + 1).equals("")) {
	    testColumns++;
	}
	System.out.println("Total columns of data are: " + testColumns);

	Object[][] data = new Object[testRows][1];
	
	int i = 0;

	for (int rowNum = dataStartRowNum; rowNum < dataStartRowNum + testRows; rowNum++) {
	    Hashtable<String, String> table = new Hashtable<String, String>();

	    for (int colNum = 0; colNum < testColumns; colNum++) {
		String testData = excel.getCellData(sheetName, colNum, rowNum);
		String columnName = excel.getCellData(sheetName, colNum, testCaseRowNum + 1);
		
		table.put(columnName, testData);
	    }
	    
	    data[i][0] = table;
	    i++;
	}

	return data;

    }

}
