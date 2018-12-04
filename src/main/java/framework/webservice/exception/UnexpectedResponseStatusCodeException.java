package framework.webservice.exception;

import framework.webservice.common.RequestType;

import java.net.URI;

public class UnexpectedResponseStatusCodeException extends RuntimeException {

	private static final long serialVersionUID = 9087646117869979774L;

	/**
	 *
	 * @param responseStatus
	 *            - http response status code
	 * @param errorMessage
	 *            - exception message
	 * @param requestUri
	 *            - URI of the reponse
	 */
	public UnexpectedResponseStatusCodeException(int responseStatus, String errorMessage, URI requestUri,
			RequestType requestType) {
		super(requestType + " " + requestUri + " returned " + responseStatus + " . Message: " + errorMessage);
	}

	public <T> UnexpectedResponseStatusCodeException(int status, T entity, URI uri, RequestType requestType) {
	}
}
