package io.github.amine.http.annotation;

import java.lang.annotation.*;

/**
 * Marks a parameter to be streamed from the HTTP request body.
 * The request must contain a JSON array.
 *
 * <p>Usage:</p>
 * <pre>
 * {@code
 * @PostMapping("/upload")
 * public void upload(@StreamBody Stream<ItemDTO> items) {
 *     items.forEach(this::process);
 * }
 * }
 * </pre>
 *
 * @author MohamedAmineOueslati
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StreamBody {
}
