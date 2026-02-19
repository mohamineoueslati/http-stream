package io.github.amine.http.config;

import io.github.amine.http.resolver.StreamBodyArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Manual configuration for non-Spring-Boot applications.
 * Users need to import this configuration.
 *
 * @author MohamedAmineOueslati
 * @since 1.0
 */
@Configuration
public class HttpStreamWebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new StreamBodyArgumentResolver());
    }
}
