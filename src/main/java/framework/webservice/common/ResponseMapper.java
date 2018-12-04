
package framework.webservice.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.ClientResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResponseMapper {

    private final ClientResponseMapper mapper;

    public ResponseMapper(final ClientResponseMapper mapper) {
        if (mapper == null) {
            throw new IllegalArgumentException("mapper was null.");
        }

        this.mapper = mapper;
    }

    public <T> Response<T> map(final ClientResponse response, final Class<T> valueType) throws IOException {

        if (response == null) {
            throw new IllegalArgumentException("response from server was null.");
        }

        final int statusCode = response.getStatus();

        T body = null;
        String errorMessage = null;

        body = mapper.map(response, valueType);

        return new Response<T>(body, statusCode, errorMessage);
    }

    public <T> Response<T> mapWithoutEntity(final ClientResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("response from server was null.");
        }

        String errorMessage = null;

        return new Response<T>(null, response.getStatus(), errorMessage);
    }

    /**
     * @param response
     * @param valueType
     * @return
     * @throws IOException
     */
    public <T> Response<List<T>> mapWithList(final ClientResponse response, final Class<T> valueType)
            throws IOException {

        if (response == null) {
            throw new IllegalArgumentException("response from server was null.");
        }

        final int statusCode = response.getStatus();

        List<T> body = null;
        String errorMessage = null;

        body = mapper.mapWithList(response, valueType);

        return new Response<List<T>>(body, statusCode, errorMessage);
    }

    public Map<String, Object> mapWithIterator(ClientResponse response) {
        String json = response.getEntity(String.class);
        Map<String, Object> result = new HashMap<>();
        JsonObject obj = new Gson().fromJson(json, JsonObject.class);
        Iterator<String> it = obj.keySet().iterator();
        while (it.hasNext()) {
            String myKey = it.next();
            JsonObject myKeyObj = obj.get(myKey).getAsJsonObject();
            result.put(myKey, myKeyObj);
        }
        return result;
    }
}