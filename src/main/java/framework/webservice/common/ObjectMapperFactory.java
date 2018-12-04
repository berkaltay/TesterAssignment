package framework.webservice.common;

import org.codehaus.jackson.map.ObjectMapper;

import static org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES;

public final class ObjectMapperFactory {
    private ObjectMapperFactory() {

    }

    public static ObjectMapper createObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}