package framework.webservice.common;

import javax.ws.rs.core.MultivaluedMap;

public class Response<T> {

    private T _body;
    private int _statusCode;
    private String responseErrorMessage;
    private MultivaluedMap<String, String> _header;

    /**
     * Constructor.
     *
     * @param body       - server rest response body
     * @param statusCode - HTTP status code
     */
    public Response(T body, int statusCode) {
        this(body, statusCode, null);
    }

    /**
     * Constructor for error case.
     *
     * @param body         - server rest response body
     * @param statusCode   - HTTP status code
     * @param errorMessage - response error message
     */
    public Response(T body, int statusCode, String errorMessage) {
        _body = body;
        _statusCode = statusCode;
        responseErrorMessage = errorMessage;
    }

    public Response(T body, int statusCode, String errorMessage, MultivaluedMap<String, String> header) {

        _body = body;
        _statusCode = statusCode;
        responseErrorMessage = errorMessage;
        _header = header;
    }

    /**
     * Returns the response body as specified result type.
     *
     * @return generic class
     */
    public T getResult() {
        return _body;
    }

    /**
     * returns http status code.
     *
     * @return HTTP status code
     */
    public int getStatusCode() {
        return _statusCode;
    }

    public String getResponseErrorMessage() {
        return responseErrorMessage;
    }

    /**
     * returns HTTP header
     *
     * @return HTTP header
     */
    public MultivaluedMap<String, String> getHeader() {
        return _header;
    }

}
