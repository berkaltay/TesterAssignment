package framework.webservice.common;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import framework.webservice.serviceclient.WaesheroesServiceClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Provides instances of service clients.
 */
public class ServiceClientFactory {

    private static ObjectMapper objectMapper;

    // init objectMapper
    static {
        objectMapper = ObjectMapperFactory.createObjectMapper();
    }

    private final WaesheroesServiceClient waesheroesServiceClient;

    public ServiceClientFactory(final Client jerseyClient) {

        waesheroesServiceClient = createWaesHeroesServiceClient(jerseyClient);

    }

    private WaesheroesServiceClient createWaesHeroesServiceClient(final Client jerseyClient) {

        final RestRequest restRequest = createRestRequest(jerseyClient);
        return new WaesheroesServiceClient(restRequest, objectMapper);
    }

    /**
     * Creates a new request via JerseyClient.
     */
    private RestRequest createRestRequest(final Client jerseyClient) {
        String uri = getServiceEndpointAddress();
        WebResource webResource = buildWebResource(jerseyClient, uri);
        return new RestRequest(webResource);
    }

    private WebResource buildWebResource(final Client jerseyClient, String uri) {
        return jerseyClient.resource(uri);
    }

    private String getServiceEndpointAddress() {
        // TODO: get uri from the config file for the given tenantProfile
        try {
            Config config = Config.getInstance();
            return config.getHost();
        } catch (IOException e) {
            throw new IllegalArgumentException("Service url is wrong.");
        }
    }

    public WaesheroesServiceClient getWaesheroesServiceClient() {
        return waesheroesServiceClient;
    }
}
