package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
//	we pass actual data to urls
//	assertions
	
	Faker faker;
	 User userpayload;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userpayload = new User();
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().emailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
//		userpayload.setUserStatus();
		
	}
	
	@Test(priority=1)
	public void testPostUser() {
		System.out.print(userpayload);
		Response response = UserEndPoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=2, dependsOnMethods = {"testPostUser"})
	public void testGetUser() {
		System.out.print(this.userpayload.getUsername());
		Response response = UserEndPoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=3, dependsOnMethods = {"testGetUser"})
	public void testUpdateUser() {
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().emailAddress());
		Response response = UserEndPoints.updateUser(this.userpayload.getUsername(),userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=4, dependsOnMethods = {"testUpdateUser"})
	public void testDeleteUser() {

		Response response = UserEndPoints.deleteUser(this.userpayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
	}
}
