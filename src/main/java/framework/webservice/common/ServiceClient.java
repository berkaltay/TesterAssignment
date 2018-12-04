package framework.webservice.common;

import org.codehaus.jackson.map.ObjectMapper;

public class ServiceClient {
    private final RestRequest restRequest;
    private final ResponseMapper msResponseMapper;

    /**
     * Sets rest request for all service clients.
     *
     * @param restRequest - for communication
     */
    public ServiceClient(final RestRequest restRequest, final ObjectMapper objectMapper) {
        if (restRequest == null) {
            throw new IllegalArgumentException("restRequest was null");
        }

        if (objectMapper == null) {
            throw new IllegalArgumentException("objectMapper was null");
        }

        this.restRequest = restRequest;
        final ClientResponseMapper mapper = new ClientResponseMapper(objectMapper);

        msResponseMapper = new ResponseMapper(mapper);
    }

    public RestRequest getRestRequest() {
        return restRequest;
    }

    public ResponseMapper getResponseMapper() {
        return msResponseMapper;
    }

}
