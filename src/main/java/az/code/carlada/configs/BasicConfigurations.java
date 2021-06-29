package az.code.carlada.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfigurations  {
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
