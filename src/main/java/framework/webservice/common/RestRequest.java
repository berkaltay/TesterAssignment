package framework.webservice.common;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import framework.webservice.exception.JsonProcessingRuntimeException;
import framework.webservice.exception.UnexpectedResponseStatusCodeException;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * REST client, uses Jersey framework for REST communication.
 */
public class RestRequest {

    private static final String IF_MATCH = "If-Match";

    private final WebResource webResource;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor of the RestRequest class
     *
     * @param webResource - set for the instance of Rest Request
     */
    public RestRequest(final WebResource webResource) {

        if (webResource == null) {
            throw new IllegalArgumentException("Creation of the Rest Request failed! resource is null...");
        }

        this.webResource = webResource;
    }

    /**
     * Sends a get request to the server without adding a method path or any
     * other parameters. This is useful for following the links.
     *
     * @return ClientResponse
     */
    public ClientResponse get() {

        ClientResponse response = executeRestRequest(RequestType.GET, webResource, null, null, null, null,
                (MediaType[]) null);
        return response;
    }

    /**
     * Sends a get request to the server with adding a method path
     *
     * @param methodPath - service method name, will be added to server URL
     * @return ClientResponse
     */
    public ClientResponse get(String methodPath) {

        ClientResponse response = executeRestRequest(RequestType.GET, methodPath, null, null, null, null,
                (MediaType[]) null);
        return response;
    }

    /**
     * Sends a REST get with parameters to the server.
     *
     * @param methodPath          - service method name, will be added to server URL
     * @param parameterMap        - list of key-value-pairs which will be used as
     *                            query-parameter in the url
     * @param responseContentType - REST response media type
     * @return ClientResponse
     */
    public ClientResponse get(final String methodPath, final MultivaluedMap<String, String> parameterMap,
                              final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.GET, methodPath, parameterMap, null, null, null,
                responseContentType);
        return response;
    }

    /**
     * Sends a get request to server with adding method path and request
     * headers.
     *
     * @param methodPath          - service method name, will be added to server URL
     * @param parameterMap        - list of key-value-pairs which will be used as
     *                            query-parameter in the url
     * @param headers             - request headers
     * @param responseContentType - REST response media type
     * @return ClientResponse
     */
    public ClientResponse get(final String methodPath, final MultivaluedMap<String, String> parameterMap,
                              final Map<String, Object> headers, final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.GET, methodPath, parameterMap, null, headers, null,
                responseContentType);
        return response;
    }

    /**
     * Sends a get request to server with all parameters
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param parameterMap         - list of key-value-pairs which will be used as
     *                             query-parameter in the url
     * @param inputEntity
     * @param headers              - request headers
     * @param parameterContentType - REST request media type
     * @param responseContentType  - REST response media type
     * @return
     */
    public ClientResponse get(final String methodPath, final MultivaluedMap<String, String> parameterMap,
                              final Object inputEntity, final Map<String, Object> headers, final MediaType parameterContentType,
                              final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.GET, methodPath, parameterMap, inputEntity, headers,
                parameterContentType, responseContentType);
        return response;
    }

    /**
     * Sends a put request to server.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param parameterMap         - list of key-value-pairs which will be used as
     *                             query-parameter in the url
     * @param inputEntity          - body of the rest request
     * @param parameterContentType - content type of the inputEntity
     * @param responseContentType  - Rest response media type
     * @retun ClientResponse
     */
    public ClientResponse put(final String methodPath, final MultivaluedMap<String, String> parameterMap,
                              final Object inputEntity, final MediaType parameterContentType, final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.PUT, methodPath, parameterMap, inputEntity, null,
                parameterContentType, responseContentType);

        return response;
    }

    /**
     * Sends a put request to server with ifMatch parameter.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param ifMatch              - header which specifies the correct resource state
     * @param inputEntity          - body of the rest request
     * @param parameterContentType - content type of the inputEntity
     * @param responseContentType  - Rest response media type
     * @return ClientResponse
     */
    public ClientResponse put(final String methodPath, final Object ifMatch, final Object inputEntity,
                              final MediaType parameterContentType, final MediaType... responseContentType) {

        if (ifMatch == null) {
            ClientResponse response = executeRestRequest(RequestType.PUT, methodPath, null, inputEntity, null,
                    parameterContentType, responseContentType);
            return response;
        }

        final HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put(IF_MATCH, ifMatch);

        ClientResponse response = executeRestRequest(RequestType.PUT, methodPath, null, inputEntity, headers,
                parameterContentType, responseContentType);

        return response;
    }

    /**
     * Sends a put request to server.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param headers              - header parameters
     * @param parameters           - list of key-value-pairs which will be used as
     *                             query-parameter in the url
     * @param inputEntity          - body of the rest request
     * @param parameterContentType - content type of the inputEntity
     * @param responseContentType  - rest response media type
     * @return ClientResponse
     */
    public ClientResponse put(final String methodPath, final Map<String, Object> headers,
                              final MultivaluedMap<String, String> parameters, final Object inputEntity,
                              final MediaType parameterContentType, final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.PUT, methodPath, parameters, inputEntity, headers,
                parameterContentType, responseContentType);

        return response;
    }

    /**
     * Sends a REST post with empty body to the server.
     *
     * @param methodPath          - service method name, will be added to server URL
     * @param responseContentType - set as REST response media type
     * @return ClientResponse
     */
    public ClientResponse post(final String methodPath, final MediaType... responseContentType) {

        ClientResponse response = post(methodPath, null, responseContentType);
        return response;
    }

    /**
     * Sends a REST post to the server with added parameters and expected
     * response type.
     *
     * @param methodPath   - service method name, will be added to server URL
     * @param parameterMap - parameters that will be added to URI
     */
    public ClientResponse post(final String methodPath, final MultivaluedMap<String, String> parameterMap,
                               final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.POST, methodPath, parameterMap, null, null, null,
                responseContentType);
        return response;
    }

    /**
     * Sends form data as a post request.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param parameterContentType - content type of the formData
     * @param formData             - formdata to sent by restRequest
     * @param responseContentType  - response type of the request
     * @return ClientResponse
     */
    public ClientResponse postFormData(final String methodPath, final MediaType parameterContentType,
                                       final Object formData, final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.POST, methodPath, null, formData, null,
                parameterContentType, responseContentType);
        return response;
    }

    /**
     * Sends a REST post to the server.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param inputEntity          - set as REST body
     * @param parameterContentType - content type of the inputEntity
     * @param responseContentType  - set as REST response media type
     * @return ClientResponse
     */
    public ClientResponse post(final String methodPath, final Object inputEntity, final MediaType parameterContentType,
                               final MediaType... responseContentType) {
        return executeRestRequest(RequestType.POST, methodPath, null, inputEntity, null, parameterContentType,
                responseContentType);
    }

    /**
     * Sends a REST post to the server.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param inputEntity          - set as REST body
     * @param parameterMap         - list of key-value-pairs which will be used as
     *                             query-parameter in the url
     * @param parameterContentType - content type of the inputEntity
     * @param responseContentType  - set as REST response media type
     * @return ClientResponse
     */
    public ClientResponse post(final String methodPath, final MultivaluedMap<String, String> parameterMap,
                               final Object inputEntity, final MediaType parameterContentType, final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.POST, methodPath, parameterMap, inputEntity, null,
                parameterContentType, responseContentType);
        return response;
    }

    /**
     * Sends a REST post to the server with headers and method path.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param parameters           - list of key-value-pairs which will be used as
     *                             query-parameter in the url
     * @param headers              - headers of the rest request
     * @param inputEntity          - set as REST body
     * @param parameterContentType - content type of the inputEntity
     * @param responseContentType  - set as REST result media type
     * @return ClientResponse
     */
    public ClientResponse post(final String methodPath, final MultivaluedMap<String, String> parameters,
                               final Map<String, Object> headers, final Object inputEntity, final MediaType parameterContentType,
                               final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.POST, methodPath, parameters, inputEntity, headers,
                parameterContentType, responseContentType);
        return response;
    }

    /**
     * Sends a REST delete to the server without adding a method path to the web
     * resource. This can be used to follow delete links.
     *
     * @return ClientResponse
     */
    public ClientResponse delete() {

        ClientResponse response = executeRestRequest(RequestType.DELETE, webResource, null, null, null, null,
                (MediaType[]) null);
        return response;
    }

    /**
     * Sends a REST delete to the server.
     *
     * @param methodPath - service method name, will be added to server URL
     * @return ClientResponse
     */
    public ClientResponse delete(final String methodPath) {

        ClientResponse response = delete(methodPath, (MultivaluedMap<String, String>) null);
        return response;
    }

    /**
     * Sends a REST delete to the server.
     *
     * @param methodPath          - service method name, will be added to server URL
     * @param responseContentType - REST response media type
     * @return ClientResponse
     */
    public ClientResponse delete(final String methodPath, final MediaType responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.DELETE, methodPath, null, null, null, null,
                responseContentType);
        return response;
    }

    /**
     * Sends a REST delete to the server.
     *
     * @param methodPath   - service method name, will be added to server URL
     * @param parameterMap - list of key-value-pairs which will be used as
     *                     query-parameter in the url
     * @return ClientResponse
     */
    public ClientResponse delete(final String methodPath, final MultivaluedMap<String, String> parameterMap) {

        ClientResponse response = executeRestRequest(RequestType.DELETE, methodPath, parameterMap, null, null, null,
                (MediaType[]) null);
        return response;
    }

    /**
     * Sends a Rest delete to the server.
     *
     * @param methodPath          - service method name, will be added to server URL
     * @param parameterMap        - list of key-value-pairs which will be used as
     *                            query-parameter in the url
     * @param ifMatch             - header which specifies the correct resource state
     * @param responseContentType - set as REST response media type
     * @return ClientResponse
     */
    public ClientResponse delete(final String methodPath, final MultivaluedMap<String, String> parameterMap,
                                 final String ifMatch, final MediaType responseContentType) {

        ClientResponse response;

        if (ifMatch == null) {
            response = executeRestRequest(RequestType.DELETE, methodPath, (parameterMap != null) ? parameterMap : null,
                    null, null, null, (responseContentType != null) ? responseContentType : (MediaType) null);
            return response;
        }

        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(IF_MATCH, ifMatch);

        response = executeRestRequest(RequestType.DELETE, methodPath, parameterMap, null, headers, (MediaType) null,
                responseContentType);
        return response;
    }

    /**
     * Sends a REST delete to the server.
     *
     * @param methodPath
     * @param inputEntity
     * @param parameterContentType
     * @param responseContentType
     * @return ClientResponse
     */
    public ClientResponse delete(final String methodPath, final Object inputEntity,
                                 final MediaType parameterContentType, final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.DELETE, methodPath, null, inputEntity, null,
                parameterContentType, responseContentType);
        return response;
    }

    /**
     * Sends a REST delete to the server with ifMatch parameter.
     *
     * @param methodPath - service method name, will be added to server URL
     * @param ifMatch    - header which specifies the correct resource state
     */
    public ClientResponse delete(final String methodPath, final Object ifMatch) {

        ClientResponse response;
        if (ifMatch == null) {
            response = executeRestRequest(RequestType.DELETE, methodPath, null, null, null, null, (MediaType[]) null);
            return response;
        }

        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(IF_MATCH, ifMatch);
        response = executeRestRequest(RequestType.DELETE, methodPath, null, null, headers, null, (MediaType[]) null);
        return response;
    }

    /**
     * Sends a REST delete with header to the server.
     *
     * @param methodPath          - service method name, will be added to server URL
     * @param header              - headers of rest request
     * @param responseContentType - REST response media type
     * @return ClientResponse
     */
    public ClientResponse delete(final String methodPath, final Map<String, Object> header,
                                 final MediaType... responseContentType) {

        ClientResponse response = executeRestRequest(RequestType.DELETE, methodPath, null, null, header, null,
                responseContentType);
        return response;
    }

    /**
     * Sends a REST patch to the server.
     *
     * @param methodPath           - service method name, will be added to server URL
     * @param ifMatch              - if match
     * @param input                - set as REST body
     * @param parameterContentType - content type of the inputEntity
     * @param responseContentType  - set as REST response media type
     * @return ClientResponse
     */
    public ClientResponse patch(final String methodPath, final String ifMatch, final Object input,
                                final MediaType parameterContentType, final MediaType... responseContentType) {

        if (ifMatch == null) {
            return executeRestRequest(RequestType.PATCH, methodPath, null, input, null, parameterContentType,
                    responseContentType);
        }

        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(IF_MATCH, ifMatch);

        return executeRestRequest(RequestType.PATCH, methodPath, null, input, headers, parameterContentType,
                responseContentType);
    }

    private ClientResponse executeRestRequest(RequestType requestType, String methodPath,
                                              MultivaluedMap<String, String> requestParameters, Object inputEntity, Map<String, Object> headers,
                                              MediaType parameterContentType, MediaType... responseContentType) {

        if (methodPath == null)
            throw new IllegalArgumentException("Method path for the RestRequest may not be null!");

        final String tmpMethodPath = ensureMethodPathStartsWithSlashAndLogPath(methodPath);
        WebResource requestWebResource = this.webResource.path(tmpMethodPath);

        ClientResponse response = executeRestRequest(requestType, requestWebResource, requestParameters, inputEntity,
                headers, parameterContentType, responseContentType);

        return response;
    }

    private ClientResponse executeRestRequest(RequestType requestType, WebResource webResource,
                                              MultivaluedMap<String, String> requestParameters, Object inputEntity, Map<String, Object> headers,
                                              MediaType parameterContentType, MediaType... responseContentType) {

        Builder builder = initializeWebResourceBuilder(webResource, requestParameters, headers, parameterContentType,
                responseContentType);

        ClientResponse response = executeRequestAndGetResponse(builder, requestType, parameterContentType, inputEntity);

        if (response != null) {
            verifyResponse(response, requestType);
        }

        return response;
    }

    private Builder initializeWebResourceBuilder(WebResource webResource,
                                                 MultivaluedMap<String, String> requestParameters, Map<String, Object> headers,
                                                 MediaType parameterContentType, MediaType... responseContentType) {

        // addQueryParams
        if (requestParameters != null && !requestParameters.isEmpty()) {
            webResource = webResource.queryParams(requestParameters);
        }

        Builder builder = webResource.getRequestBuilder();
        // Initialize web resource
        if (parameterContentType != null)
            builder.type(parameterContentType);
        if (responseContentType != null && responseContentType.length > 0)
            builder.accept(responseContentType);
        if (headers != null)
            headers.entrySet().forEach(item -> builder.header(item.getKey(), String.valueOf(item.getValue())));

        return builder;
    }

    private ClientResponse executeRequestAndGetResponse(Builder builder, RequestType requestType,
                                                        MediaType parameterContentType, Object inputEntity) {

        ClientResponse response;
        final Object convertedInputEntity = convertInputEntityToRequiredFormat(inputEntity, parameterContentType);

        try {
            switch (requestType) {
                case GET:
                    response = builder.get(ClientResponse.class);
                    break;
                case POST:
                    response = builder.post(ClientResponse.class, convertedInputEntity);
                    break;
                case PUT:
                    response = builder.put(ClientResponse.class, convertedInputEntity);
                    break;
                case DELETE:
                    response = (convertedInputEntity != null) ? builder.delete(ClientResponse.class, convertedInputEntity)
                            : builder.delete(ClientResponse.class);
                    break;
                case HEAD:
                    response = builder.head();
                    break;
                case PATCH:
                    response = builder.method("PATCH", ClientResponse.class, convertedInputEntity);
                    break;
                default:
                    throw new UnsupportedOperationException("Rest request type not supported: " + requestType);
            }
        } catch (ClientHandlerException exception) {
            if (exception.getCause() instanceof SocketTimeoutException)
                throw new ClientHandlerException("Server does not respond in given time! ", exception);

            throw exception;
        }

        // TODO: how to deal with null response
        return response;
    }

    private void verifyResponse(ClientResponse response, RequestType requestType) {
        // verify the response
        int responseStatus = response.getStatus();

        // TODO: which status codes considered as problematic is not clear yet.
        // These ones might be updated in the
        // future. Clarify this.
        if (responseStatus == HttpStatus.SC_INTERNAL_SERVER_ERROR || responseStatus >= HttpStatus.SC_BAD_GATEWAY) {
            throw new UnexpectedResponseStatusCodeException(response.getStatus(), response.getEntity(String.class),
                    webResource.getURI(), requestType);
        }
    }

    private Object convertInputEntityToRequiredFormat(Object input, MediaType contentMediaType) {

        if (input == null || contentMediaType == null)
            return null;

        switch (contentMediaType.toString().split(";")[0]) {
            case MediaType.TEXT_PLAIN:
                return input.toString();
            case MediaType.MULTIPART_FORM_DATA:
            case MediaType.APPLICATION_OCTET_STREAM:
            case MediaType.WILDCARD:
            case MediaType.APPLICATION_JSON:
                try {
                    return objectMapper.writeValueAsString(input);
                } catch (Exception e) {
                    throw new JsonProcessingRuntimeException(
                            "Exception happened during the JSON processing! Input json: " + input.toString() + " - Cause: ",
                            e);
                }
            default:
                throw new UnsupportedOperationException("MediaType not supported! Type:" + contentMediaType);
        }

    }

    private String ensureMethodPathStartsWithSlashAndLogPath(final String methodPath) {
        String tmpMethodPath = methodPath;
        if (methodPath != "") {
            tmpMethodPath = methodPath.startsWith("/") ? methodPath : "/" + methodPath;
        }
        return tmpMethodPath;
    }
}
