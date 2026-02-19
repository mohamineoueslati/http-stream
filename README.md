# HTTP Stream

A lightweight Java library that enables efficient streaming of JSON arrays from HTTP request bodies without loading them entirely into memory. Perfect for processing large datasets in web applications.

## Features

- **Memory-Efficient Streaming**: Process large JSON arrays without loading them completely into memory
- **Spring Framework Support**: Seamless integration with Spring Web MVC
- **Spring Boot Auto-Configuration**: Zero-configuration setup for Spring Boot applications
- **Clean API**: Simple and intuitive `@StreamBody` annotation for method parameters
- **Type-Safe**: Full type support with automatic JSON deserialization

## Project Structure

HTTP Stream is organized as a Maven multi-module project:

- **http-stream-core**: Core streaming functionality with JSON parsing
- **http-stream-spring**: Spring Web MVC integration
- **http-stream-spring-boot-starter**: Spring Boot auto-configuration for zero-setup deployment

## Quick Start

### For Spring Boot

Add the dependency:

```xml
<dependency>
    <groupId>io.github.mohamineoueslati</groupId>
    <artifactId>http-stream-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Use in your controller:

```java
@RestController
@RequestMapping("/api")
public class ItemController {

    @PostMapping("/items/process")
    public ResponseEntity<String> processItems(@StreamBody Stream<ItemDTO> items) {
        items.forEach(item -> {
            // Process each item without loading entire stream
            System.out.println("Processing: " + item.getName());
        });
        return ResponseEntity.ok("Processing complete");
    }
}
```

Send a JSON array request:

```bash
curl -X POST http://localhost:8080/api/items/process \
  -H "Content-Type: application/json" \
  -d '[
    {"id": 1, "name": "Item 1"},
    {"id": 2, "name": "Item 2"},
    {"id": 3, "name": "Item 3"}
  ]'
```

### For Manual Spring Configuration (non-Boot)

Add the dependency:

```xml
<dependency>
    <groupId>io.github.mohamineoueslati</groupId>
    <artifactId>http-stream-spring</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Import the configuration in your Spring application:

```java
@Configuration
@Import(HttpStreamWebConfig.class)
public class WebConfig {
    // Your configuration
}
```

## System Requirements

### JDK Compatibility
- **Java**: 17 or higher (tested and compatible with Java 17-23+)
  - Minimum: Java 17 (required for Jakarta EE and modern Java features)
  - Recommended: Java 21 LTS or latest stable version
  - Forward compatible: Works with future Java versions

### Build and Framework Requirements
- **Maven**: 3.8.1 or higher
- **Spring Framework**: 6.0.4 or higher (6.0.x, 6.1.x, 7.0.x)
  - Minimum: Spring Framework 6.0.4 (required for Jakarta EE support)
  - Latest tested: Spring Framework 7.0.4
- **Spring Boot**: 3.0.0 or higher (3.0.x, 3.1.x, 3.2.x, 4.0.x)
  - Minimum: Spring Boot 3.0.0 (required for @AutoConfiguration)
  - Latest tested: Spring Boot 4.0.2

### Version Compatibility Matrix
| Java Version | Spring Framework | Spring Boot | Status |
|---|---|---|---|
| 17 | 6.0.4+ | 3.0.0+ | ✅ Supported |
| 21 | 6.0.4+ | 3.0.0+ | ✅ Supported (Recommended) |
| 23 | 6.0.4+ | 3.0.0+ | ✅ Supported |
| 25+ | 6.0.4+ | 3.0.0+ | ✅ Compatible |

## Building

Build the entire project:

```bash
mvn clean install
```

Build a specific module:

```bash
mvn clean install -pl http-stream-core
```

Run tests:

```bash
mvn test
```

## Project Information

- **Group ID**: `io.github.mohamineoueslati`
- **Version**: `1.0.0-SNAPSHOT`
- **License**: Apache License 2.0
- **Repository**: [github.com/mohamineoueslati/http-stream](https://github.com/mohamineoueslati/http-stream)

## Developer

**MohamedAmine Oueslati**
- Email: mohamad.amine.oueslati@gmail.com
- GitHub: [mohamineoueslati](https://github.com/mohamineoueslati)

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

