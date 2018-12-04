package backend.testclass;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import framework.webservice.common.Response;
import framework.webservice.common.RestRequest;
import framework.webservice.data.UserInfoDto;
import framework.webservice.data.UserInfoResponse;
import framework.webservice.serviceclient.WaesheroesServiceClient;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.map.ObjectMapper;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WaesheroesServiceClientTest {

    private static final String WAESHEROES_URI = "/waesheroes/api/v1";
    private static final String USERS_URI = "/users";
    private static final String DETAILS_URI = "/details";
    private static final String ALL_URI = "/all";
    private static final String LOGIN_URI = "/access";

    private WaesheroesServiceClient waesheroesServiceClient;
    private RestRequest requestMock;
    private ObjectMapper objectMapper;
    private ClientResponse responseMock;

    @BeforeMethod
    public void initialize() {
        requestMock = Mockito.mock(RestRequest.class);
        responseMock = Mockito.mock(ClientResponse.class);
        objectMapper = new ObjectMapper();
        waesheroesServiceClient = new WaesheroesServiceClient(requestMock, objectMapper);
    }

    @Test
    public void getAllUsers_withMockedValues_RightMethodsAreCalled() {

        Mockito.when(requestMock.get(WAESHEROES_URI + USERS_URI + ALL_URI, null, MediaType.APPLICATION_JSON_TYPE))
                .thenReturn(responseMock);

        final String entityMock = getAllUserEntity();
        Mockito.when(responseMock.getEntity(String.class)).thenReturn(entityMock);
        Mockito.when(responseMock.getStatus()).thenReturn(HttpStatus.SC_OK);

        String json = responseMock.getEntity(String.class);
        Map<String, Object> result = new HashMap<>();
        JsonObject obj = new Gson().fromJson(json, JsonObject.class);
        Iterator<String> it = obj.keySet().iterator();
        while (it.hasNext()) {
            String myKey = it.next();
            JsonObject myKeyObj = obj.get(myKey).getAsJsonObject();
            result.put(myKey, myKeyObj);
        }
        // WHEN
        final Map<String, Object> response = waesheroesServiceClient
                .getAllUsers();
        // THEN
        Assert.assertEquals(response.size(), result.size());
        Mockito.verify(requestMock).get(WAESHEROES_URI + USERS_URI + ALL_URI, null, MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void createUser_withMockedValues_RightMethodsAreCalled() throws IOException {
        // GIVEN
        Map<String, Object> headers = new HashMap<>();
        headers.put("username", "username");
        final UserInfoDto userInfoDto = Mockito.mock(UserInfoDto.class);
        Mockito.when(requestMock.post(WAESHEROES_URI + USERS_URI, null, headers, userInfoDto, MediaType.APPLICATION_JSON_TYPE)).thenReturn(responseMock);

        final String entityMock = createUserEntity();
        Mockito.when(responseMock.getEntity(String.class)).thenReturn(entityMock);
        Mockito.when(responseMock.getStatus()).thenReturn(HttpStatus.SC_OK);

        final UserInfoResponse userInfoResponse = objectMapper.readValue(entityMock, UserInfoResponse.class);

        // WHEN
        final Response<UserInfoResponse> response = waesheroesServiceClient.createNewUser(userInfoDto, "username");

        // THEN
        Assert.assertNotNull(response.getResult(), "MS Response Result was null");
        Assert.assertEquals(response.getResult().toString(), userInfoResponse.toString(), "result is wrong");
        Assert.assertEquals(response.getStatusCode(), responseMock.getStatus(), "status is wrong");
        Mockito.verify(requestMock).post(WAESHEROES_URI + USERS_URI, null, headers, userInfoDto, MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void loginUser_withMockedValues_RightMethodsAreCalled() throws IOException {
        // GIVEN

        Mockito.when(requestMock.get(WAESHEROES_URI + USERS_URI + LOGIN_URI, null, MediaType.APPLICATION_JSON_TYPE)).thenReturn(responseMock);

        final String entityMock = createUserEntity();
        Mockito.when(responseMock.getEntity(String.class)).thenReturn(entityMock);
        Mockito.when(responseMock.getStatus()).thenReturn(HttpStatus.SC_OK);

        final UserInfoResponse userInfoResponse = objectMapper.readValue(entityMock, UserInfoResponse.class);

        // WHEN
        final Response<UserInfoResponse> response = waesheroesServiceClient.loginUser();

        // THEN
        Assert.assertNotNull(response.getResult(), "MS Response Result was null");
        Assert.assertEquals(response.getResult().toString(), userInfoResponse.toString(), "result is wrong");
        Assert.assertEquals(response.getStatusCode(), responseMock.getStatus(), "status is wrong");
        Mockito.verify(requestMock).get(WAESHEROES_URI + USERS_URI + LOGIN_URI, null, MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void getUserByName_withMockedValues_RightMethodsAreCalled() throws IOException {
        // GIVEN
        final MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
        parameters.add("username", String.valueOf("username"));
        Mockito.when(requestMock.get(WAESHEROES_URI + USERS_URI + DETAILS_URI, parameters, MediaType.APPLICATION_JSON_TYPE)).thenReturn(responseMock);

        final String entityMock = createUserEntity();
        Mockito.when(responseMock.getEntity(String.class)).thenReturn(entityMock);
        Mockito.when(responseMock.getStatus()).thenReturn(HttpStatus.SC_OK);

        final UserInfoResponse userInfoResponse = objectMapper.readValue(entityMock, UserInfoResponse.class);

        // WHEN
        final Response<UserInfoResponse> response = waesheroesServiceClient.getUserByName("username");

        // THEN
        Assert.assertNotNull(response.getResult(), "MS Response Result was null");
        Assert.assertEquals(response.getResult().toString(), userInfoResponse.toString(), "result is wrong");
        Assert.assertEquals(response.getStatusCode(), responseMock.getStatus(), "status is wrong");
        Mockito.verify(requestMock).get(WAESHEROES_URI + USERS_URI + DETAILS_URI, parameters, MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void updateUser_withMockedValues_RightMethodsAreCalled() throws IOException {
        // GIVEN
        final UserInfoDto userInfoDto = Mockito.mock(UserInfoDto.class);
        Mockito.when(requestMock.put(WAESHEROES_URI + USERS_URI, null, userInfoDto, MediaType.APPLICATION_JSON_TYPE)).thenReturn(responseMock);

        final String entityMock = createUserEntity();
        Mockito.when(responseMock.getEntity(String.class)).thenReturn(entityMock);
        Mockito.when(responseMock.getStatus()).thenReturn(HttpStatus.SC_OK);

        final UserInfoResponse userInfoResponse = objectMapper.readValue(entityMock, UserInfoResponse.class);

        // WHEN
        final Response<UserInfoResponse> response = waesheroesServiceClient.updateUser(userInfoDto);

        // THEN
        Assert.assertNotNull(response.getResult(), "MS Response Result was null");
        Assert.assertEquals(response.getResult().toString(), userInfoResponse.toString(), "result is wrong");
        Assert.assertEquals(response.getStatusCode(), responseMock.getStatus(), "status is wrong");
        Mockito.verify(requestMock).put(WAESHEROES_URI + USERS_URI, null, userInfoDto, MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void deleteUser_withMockedValues_RightMethodsAreCalled() throws IOException {
        // GIVEN

        Mockito.when(requestMock.delete(WAESHEROES_URI + USERS_URI)).thenReturn(responseMock);

        final String entityMock = createUserEntity();
        Mockito.when(responseMock.getEntity(String.class)).thenReturn(entityMock);
        Mockito.when(responseMock.getStatus()).thenReturn(HttpStatus.SC_OK);

        final UserInfoResponse userInfoResponse = objectMapper.readValue(entityMock, UserInfoResponse.class);

        // WHEN
        final Response<UserInfoResponse> response = waesheroesServiceClient.deleteUser();

        // THEN
        Assert.assertNotNull(response.getResult(), "MS Response Result was null");
        Assert.assertEquals(response.getResult().toString(), userInfoResponse.toString(), "result is wrong");
        Assert.assertEquals(response.getStatusCode(), responseMock.getStatus(), "status is wrong");
        Mockito.verify(requestMock).delete(WAESHEROES_URI + USERS_URI);
    }

    private String getAllUserEntity() {
        return "{\"dev\":{\"dateOfBirth\":\"1984-09-18\",\"email\":\"new.use3r@wearewaes.com\",\"isAdmin\":false,\"name\":\"New User\",\"superpower\":\"Kamehameha.\"},\"tester\":{\"dateOfBirth\":\"1999-10-10\",\"email\":\"zd.dev@wearewaes.com\",\"isAdmin\":false,\"name\":\"Zuper Dooper Dev\",\"superpower\":\"A new power.\"},\"admin\":{\"dateOfBirth\":\"1984-04-16\",\"email\":\"a.admin@wearewaes.com\",\"isAdmin\":true,\"name\":\"Amazing Admin\",\"superpower\":\"Change the course of a waterfall.\"}}";
    }

    private String createUserEntity() {
        return "{\"dateOfBirth\":\"1984-09-18\",\"email\":\"new.user@wearewaes.com\",\"isAdmin\":false,\"name\":\"New User\",\"superpower\":\"Kamehameha.\"}";
    }
}
