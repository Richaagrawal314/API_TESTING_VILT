package API_TESTING.project_1;

import org.hamcrest.core.IsEqual;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

import java.util.HashMap;

import io.restassured.specification.ResponseSpecification;

public class TestCases {
	
	ResponseSpecification res;
	@BeforeClass
	public void setSpecification() 
		{
		res = RestAssured.expect();
		res.statusLine("HTTP/1.1 200 OK");
		res.contentType(ContentType.JSON);
		ExtentReportManager.createReport();
	}
	
	@Test
	public void testFetchUser() {
		ExtentReportManager.test= ExtentReportManager.reports.startTest("testFetchUser","Fetch the user details ");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/users/2");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "statusCode");
		RestAssured.baseURI="https://reqres.in/";
		//given().when().get("/api/users?page=2").then().statusCode(200);
		given().when().get("/api/users?page=2").then().spec(res).assertThat().body("page", IsEqual.equalTo(2));
		
	}
	

	@Test
	public void testCreateUser() {
		ExtentReportManager.test= ExtentReportManager.reports.startTest("testCreateUser","Fetch the user details ");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/users");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "name:Richa");
		RestAssured.baseURI="https://reqres.in/";
		HashMap<String,String> params=new HashMap<String, String>();
		params.put("name", "Richa");
		params.put("job", "QA");
		given().when().contentType("application/json").body(params).post("/api/users").then().assertThat().body("name", IsEqual.equalTo("Richa"));
		
	}
	
	
	@Test
	public void testRegisterUser() {
		ExtentReportManager.test= ExtentReportManager.reports.startTest("testRegisterUser","Fetch the user details ");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/register");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "id:4");
		RestAssured.baseURI="https://reqres.in/";
		HashMap<String,String> params=new HashMap<String, String>();
		params.put("email", "eve.holt@reqres.in");
		params.put("password", "pistol");
		String tokenS =given().when().contentType("application/json").body(params).post("/api/register").then().assertThat().body("id", IsEqual.equalTo(4)).extract().path("token");
		System.out.println("\n token for successful registration is "+tokenS+"\n");
		ExtentReportManager.test.log(LogStatus.INFO, "Field fetched: token",tokenS );
	}
	
	
	@Test
	public void testLoginUserSuccessful() {
		ExtentReportManager.test= ExtentReportManager.reports.startTest("testLoginUserSuccessful","Fetch the user details ");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/login");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "statusCode:200");
		RestAssured.baseURI="https://reqres.in/";
		HashMap<String,String> params=new HashMap<String, String>();
		params.put("email", "eve.holt@reqres.in");
		params.put("password", "cityslicka");
		String tokenU =given().when().contentType("application/json").body(params).post("/api/login").then().assertThat().statusCode(200).extract().path("token");
		System.out.println("\n token for successful Login is "+tokenU+"\n");
		ExtentReportManager.test.log(LogStatus.INFO, "Field fetched: token",tokenU );
	}
	
	
	
	@Test
	public void testLoginUserUnsuccessful() {
		ExtentReportManager.test= ExtentReportManager.reports.startTest("testLoginUserUnsuccessful","Fetch the user details ");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/login");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "statusCode:400");
		RestAssured.baseURI="https://reqres.in/";
		HashMap<String,String> params=new HashMap<String, String>();
		params.put("email", "eve.holt@reqres.in");
		String errorS =given().when().contentType("application/json").body(params).post("/api/login").then().assertThat().statusCode(400).extract().path("error");
		System.out.println("\n error is "+errorS+"\n");
		ExtentReportManager.test.log(LogStatus.INFO, "Field fetched: error",errorS );
	}
		@Test
		public void testGetUserList() {
			ExtentReportManager.test= ExtentReportManager.reports.startTest("testGetUserList","Fetch the user details ");
			ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
			ExtentReportManager.test.log(LogStatus.INFO, "API Call", "GET");
			ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/unknown");
			ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "statusCode:200");
			RestAssured.baseURI="https://reqres.in/";
			HashMap<String,String> params=new HashMap<String, String>();
			params.put("email", "eve.holt@reqres.in");
			params.put("password", "pistol");
			String UserList =given().when().contentType("application/json").get("/api/unknown").then().assertThat().statusCode(200).extract().asString();
			System.out.println("\n UserList is "+UserList+"\n");
			ExtentReportManager.test.log(LogStatus.INFO, "Field fetched: UserList  ",UserList );
		}
		
		@AfterClass
		public void closeReport()
		{
			ExtentReportManager.reports.flush();
		}
	
}
