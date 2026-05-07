package io.github.amine.http.core;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Core utility to create Java Streams from HTTP requests
 */
@Slf4j
public final class RequestStream {

    private RequestStream() {}

    /**
     * Creates a Stream from a JSON array HTTP request with custom ObjectMapper
     * @param request The HTTP request containing JSON array
     * @param elementType The class of elements in the array
     * @param mapper Custom ObjectMapper for JSON parsing
     * @return Stream of parsed objects
     */
    public static <T> Stream<T> from(HttpServletRequest request,
                                     Class<T> elementType,
                                     ObjectMapper mapper) {
        try {
            JsonParser parser = mapper.createParser(request.getInputStream());

            // Check if it starts with array
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalArgumentException("Request body must be a JSON array");
            }

            // Create iterator-like parser wrapper
            JsonArrayIterator<T> iterator = new JsonArrayIterator<>(parser, mapper, elementType);

            // Create spliterator for proper streaming
            Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(
                    iterator,
                    Spliterator.ORDERED | Spliterator.NONNULL
            );

            // Return stream that will auto-close the parser
            return StreamSupport.stream(spliterator, false)
                    .onClose(parser::close);

        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create stream from request", e);
        }
    }

    /**
     * Creates a Stream from a JSON array HTTP request using JavaType
     * @param request The HTTP request containing JSON array
     * @param javaType The Jackson JavaType of elements in the array
     * @param mapper Custom ObjectMapper for JSON parsing
     * @return Stream of parsed objects
     */
    public static <T> Stream<T> from(HttpServletRequest request,
                                     JavaType javaType,
                                     ObjectMapper mapper) {
        try {
            JsonParser parser = mapper.createParser(request.getInputStream());

            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalArgumentException("Request body must be a JSON array");
            }

            JsonArrayIterator<T> iterator = new JsonArrayIterator<>(parser, mapper, javaType);

            Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(
                    iterator,
                    Spliterator.ORDERED | Spliterator.NONNULL
            );

            return StreamSupport.stream(spliterator, false)
                    .onClose(parser::close);

        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create stream from request", e);
        }
    }
}