package framework.webservice.common;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import java.util.concurrent.TimeUnit;

public class JerseyClientFactory {
    private static final int READ_TIME_OUT = (int) TimeUnit.MINUTES.toMillis(5);

    private JerseyClientFactory() {
        // hidden constructor
    }

    public static Client create() {
        final DefaultClientConfig config = new DefaultClientConfig();
        config.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND, true);
        final Client client = Client.create(config);
        client.setReadTimeout(READ_TIME_OUT);
        client.setFollowRedirects(true);
        System.setProperty("http.maxRedirects", "200");
        return client;
    }

    public static Client create(String userName,String password) {
        final DefaultClientConfig config = new DefaultClientConfig();
        config.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND, true);
        final Client client = Client.create(config);
        client.setReadTimeout(READ_TIME_OUT);
        client.setFollowRedirects(true);
        System.setProperty("http.maxRedirects", "200");
        final HTTPBasicAuthFilter authFilter = new HTTPBasicAuthFilter(userName,
                password);
        client.addFilter(authFilter);
        return client;
    }

}
