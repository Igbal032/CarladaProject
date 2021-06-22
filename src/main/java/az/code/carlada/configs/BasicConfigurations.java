package az.code.carlada.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfigurations {
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
