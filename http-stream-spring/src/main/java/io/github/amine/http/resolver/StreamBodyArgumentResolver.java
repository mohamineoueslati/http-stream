package io.github.amine.http.resolver;

import io.github.amine.http.core.RequestStream;
import io.github.amine.http.annotation.StreamBody;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.stream.Stream;

/**
 * Resolves {@link Stream} method parameters annotated with {@link StreamBody}.
 *
 * @author MohamedAmineOueslati
 * @since 1.0
 */
public class StreamBodyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(StreamBody.class) &&
                Stream.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalStateException("HttpServletRequest not found");
        }

        ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
        Class<?> elementType = resolvableType.getGeneric(0).resolve();

        if (elementType == null) {
            throw new IllegalArgumentException("Could not resolve element type of Stream");
        }

        return RequestStream.from(request, elementType);
    }
}