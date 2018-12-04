package framework.webservice.serviceclient;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import framework.webservice.common.Response;
import framework.webservice.common.RestRequest;
import framework.webservice.common.ServiceClient;
import framework.webservice.data.UserInfoDto;
import framework.webservice.data.UserInfoResponse;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WaesheroesServiceClient extends ServiceClient {

    private static final String WAESHEROES_URI = "/waesheroes/api/v1";
    private static final String USERS_URI = "/users";
    private static final String DETAILS_URI = "/details";
    private static final String ALL_URI = "/all";
    private static final String LOGIN_URI = "/access";

    public WaesheroesServiceClient(RestRequest restRequest, ObjectMapper objectMapper) {
        super(restRequest, objectMapper);
    }

    public Response<UserInfoResponse> getUserByName(final String username) throws IOException {

        final ClientResponse response = getUserByNameRaw(username);
        return getResponseMapper().map(response, UserInfoResponse.class);
    }

    private ClientResponse getUserByNameRaw(final String username) {
        final MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
        parameters.add("username", String.valueOf(username));
        return getRestRequest().get(WAESHEROES_URI + USERS_URI + DETAILS_URI, parameters, MediaType.APPLICATION_JSON_TYPE);
    }

    public Map<String, Object> getAllUsers()  {

        final ClientResponse response = getAllUsersRaw();
        return getResponseMapper().mapWithIterator(response);
    }

    private ClientResponse getAllUsersRaw() {
        return getRestRequest().get(WAESHEROES_URI + USERS_URI + ALL_URI, null, MediaType.APPLICATION_JSON_TYPE);
    }

    public Response<UserInfoResponse> loginUser() throws IOException {

        final ClientResponse response = loginUserRaw();
        return getResponseMapper().map(response, UserInfoResponse.class);
    }

    private ClientResponse loginUserRaw() {
        return getRestRequest().get(WAESHEROES_URI + USERS_URI + LOGIN_URI, null, MediaType.APPLICATION_JSON_TYPE);
    }

    public Response<UserInfoResponse> createNewUser(UserInfoDto userInfoDto, String username) throws IOException {

        final ClientResponse response = createNewUserRaw(userInfoDto, username);
        return getResponseMapper().map(response, UserInfoResponse.class);
    }

    private ClientResponse createNewUserRaw(UserInfoDto userInfoDto, String username) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("username", username);

        return getRestRequest().post(WAESHEROES_URI + USERS_URI, null, headers, userInfoDto, MediaType.APPLICATION_JSON_TYPE);
    }

    public Response<UserInfoResponse> updateUser(UserInfoDto userInfoDto) throws IOException {

        final ClientResponse response = updateUserRaw(userInfoDto);
        return getResponseMapper().map(response, UserInfoResponse.class);
    }

    private ClientResponse updateUserRaw(UserInfoDto userInfoDto) {

        return getRestRequest().put(WAESHEROES_URI + USERS_URI, null, userInfoDto, MediaType.APPLICATION_JSON_TYPE);
    }

    public Response<UserInfoResponse> deleteUser() throws IOException {

        final ClientResponse response = deleteUserRaw();
        return getResponseMapper().map(response, UserInfoResponse.class);
    }

    private ClientResponse deleteUserRaw() {

        return getRestRequest().delete(WAESHEROES_URI + USERS_URI);
    }
}

