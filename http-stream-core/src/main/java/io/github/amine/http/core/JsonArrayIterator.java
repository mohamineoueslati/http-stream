package io.github.amine.http.core;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;

/**
 * Iterator that lazily reads from JsonParser
 */
class JsonArrayIterator<T> implements java.util.Iterator<T> {
    private final JsonParser parser;
    private final Class<T> type;
    private boolean hasNext = true;

    JsonArrayIterator(JsonParser parser, Class<T> type) {
        this.parser = parser;
        this.type = type;
    }

    @Override
    public boolean hasNext() {
        if (!hasNext) return false;

        JsonToken token = parser.nextToken();
        hasNext = token != JsonToken.END_ARRAY;
        return hasNext;
    }

    @Override
    public T next() {
        return parser.readValueAs(type);
    }
}