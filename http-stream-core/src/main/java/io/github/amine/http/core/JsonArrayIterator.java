package io.github.amine.http.core;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator that lazily reads from JsonParser
 */
class JsonArrayIterator<T> implements Iterator<T> {
    private final JsonParser parser;
    private final ObjectMapper mapper;
    private final JavaType javaType;
    private final Class<T> type;
    private Boolean nextTokenAvailable = null;

    JsonArrayIterator(JsonParser parser, ObjectMapper mapper, Class<T> type) {
        this.parser = parser;
        this.mapper = mapper;
        this.type = type;
        this.javaType = null;
    }

    JsonArrayIterator(JsonParser parser, ObjectMapper mapper, JavaType javaType) {
        this.parser = parser;
        this.mapper = mapper;
        this.javaType = javaType;
        this.type = null;
    }

    @Override
    public boolean hasNext() {
        if (nextTokenAvailable != null) {
            return nextTokenAvailable;
        }

        JsonToken token = parser.nextToken();
        nextTokenAvailable = (token != null && token != JsonToken.END_ARRAY);
        return nextTokenAvailable;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        T value;
        if (javaType != null) {
            value = mapper.readValue(parser, javaType);
        } else {
            value = mapper.readValue(parser, type);
        }
        nextTokenAvailable = null; // Consume the token
        return value;
    }
}