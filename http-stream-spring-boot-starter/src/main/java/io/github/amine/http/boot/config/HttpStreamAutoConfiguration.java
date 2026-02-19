package io.github.amine.http.boot.config;

import io.github.amine.http.annotation.StreamBody;
import io.github.amine.http.config.HttpStreamWebConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Autoconfiguration for http-stream in Spring Boot applications.
 *
 * @author MohamedAmineOueslati
 * @since 1.0
 */
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnClass({StreamBody.class, WebMvcConfigurer.class})
public class HttpStreamAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(HttpStreamWebConfig.class)
    public WebMvcConfigurer httpStreamWebConfig() {
        return new HttpStreamWebConfig();
    }

}
