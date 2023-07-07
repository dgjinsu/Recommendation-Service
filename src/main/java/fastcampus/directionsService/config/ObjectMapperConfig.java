package fastcampus.directionsService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapperConfig objectMapper() {
        return new ObjectMapperConfig();
    }
}
