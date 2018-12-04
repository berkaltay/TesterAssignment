package backend.testclass;

import framework.base.WSTestBase;
import framework.extentFactory.ReportFactory;
import framework.webservice.common.JerseyClientFactory;
import framework.webservice.common.Response;
import framework.webservice.common.ServiceClientFactory;
import framework.webservice.data.UserInfoDto;
import framework.webservice.data.UserInfoResponse;
import framework.webservice.serviceclient.WaesheroesServiceClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class WaesBackEndTest extends WSTestBase {

    private ServiceClientFactory serviceClientFactory = new ServiceClientFactory(JerseyClientFactory.create());
    private ServiceClientFactory serviceClientFactoryAll = new ServiceClientFactory(JerseyClientFactory.create("admin", "hero"));
    private ServiceClientFactory serviceClientFactoryUpdate;
    private WaesheroesServiceClient waesheroesServiceClient;
    private int totalUserCount;
    Random random = new Random();
    private int number = random.nextInt(10);
    private String userName = "Test" + number;
    private UserInfoDto userInfoDto = new UserInfoDto("false", "1984-09-18", "new.user" + number + "@wearewaes.com", userName, "wololo", "Kamehameha.");

    @Test
    public void runUniTestsForServices() throws IOException {

    }

    @Test
    public void testAllServices() throws IOException {

        waesheroesServiceClient = serviceClientFactoryAll.getWaesheroesServiceClient();
        Map<String, Object> userList = waesheroesServiceClient.getAllUsers();
        totalUserCount = userList.size();

        waesheroesServiceClient = serviceClientFactory.getWaesheroesServiceClient();
        Response<UserInfoResponse> createuserresponse = waesheroesServiceClient.createNewUser(userInfoDto, userName);
        Assert.assertEquals(createuserresponse.getStatusCode(), 200);
        Assert.assertEquals(createuserresponse.getResult().getName(), userInfoDto.getName());
        Assert.assertEquals(createuserresponse.getResult().getDateOfBirth(), userInfoDto.getDateOfBirth());
        Assert.assertEquals(createuserresponse.getResult().getEmail(), userInfoDto.getEmail());
        Assert.assertEquals(createuserresponse.getResult().getIsAdmin(), userInfoDto.getIsadmin());
        Assert.assertEquals(createuserresponse.getResult().getSuperpower(), userInfoDto.getSuperpower());
        ReportFactory.getChildTest().pass("User Created Successfully");

        waesheroesServiceClient = serviceClientFactoryAll.getWaesheroesServiceClient();
        userList = waesheroesServiceClient.getAllUsers();
        Assert.assertEquals(userList.size(), totalUserCount + 1);

        waesheroesServiceClient = serviceClientFactory.getWaesheroesServiceClient();
        Response<UserInfoResponse> response = waesheroesServiceClient.getUserByName(userName);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getResult().getName(), userInfoDto.getName());
        Assert.assertEquals(response.getResult().getDateOfBirth(), userInfoDto.getDateOfBirth());
        Assert.assertEquals(response.getResult().getEmail(), userInfoDto.getEmail());
        Assert.assertEquals(response.getResult().getIsAdmin(), userInfoDto.getIsadmin());
        Assert.assertEquals(response.getResult().getSuperpower(), userInfoDto.getSuperpower());
        ReportFactory.getChildTest().pass("Retrieving User information successful");

        userInfoDto.setEmail("test@test.com");
        serviceClientFactoryUpdate = new ServiceClientFactory(JerseyClientFactory.create(userName, userInfoDto.getPw()));
        waesheroesServiceClient = serviceClientFactoryUpdate.getWaesheroesServiceClient();
        Response<UserInfoResponse> updateUserresponse = waesheroesServiceClient.updateUser(userInfoDto);
        Assert.assertEquals(updateUserresponse.getStatusCode(), 200);
        ReportFactory.getChildTest().pass("User Info Update Successful");

        Response<UserInfoResponse> loginresponse = waesheroesServiceClient.loginUser();
        Assert.assertEquals(loginresponse.getStatusCode(), 200);
        ReportFactory.getChildTest().pass("Login with service successful");

        Response<UserInfoResponse> deleteresponse = waesheroesServiceClient.deleteUser();
        Assert.assertEquals(deleteresponse.getStatusCode(), 200);
        ReportFactory.getChildTest().pass("User Deleted Successfully");
    }
}
