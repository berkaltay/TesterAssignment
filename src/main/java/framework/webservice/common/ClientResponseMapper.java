package framework.webservice.common;

import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ClientResponseMapper {

    private final ObjectMapper objectMapper;

    public ClientResponseMapper(final ObjectMapper objectMapper) {
        if (objectMapper == null) {
            throw new IllegalArgumentException("object mapper was null");
        }

        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    public <T> T map(final ClientResponse response, final Class<T> valueType) throws IOException {
        final String entity = response.getEntity(String.class);
        T body = null;

        if (valueType.equals(String.class)) {
            body = (T) entity;
        } else if (valueType.equals(UUID.class)) {
            body = (T) UUID.fromString(entity);
        } else if (valueType.equals(Boolean.class)) {
            body = (T) response.getEntity(Boolean.class);
        } else {
            body = objectMapper.readValue(entity, valueType);
        }

        return body;
    }

    public <T> List<T> mapWithList(final ClientResponse response, final Class<T> valueType) throws IOException {
        final String entity = response.getEntity(String.class);
        final CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class,
                valueType);

        return objectMapper.readValue(entity, collectionType);
    }

}
