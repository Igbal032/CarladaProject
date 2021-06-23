package az.code.carlada.configs;

import az.code.carlada.components.TokenInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BasicConfigurations implements WebMvcConfigurer {
    final
    TokenInterceptor productServiceUserTokenInterceptor;

    public BasicConfigurations(TokenInterceptor productServiceUserTokenInterceptor) {
        this.productServiceUserTokenInterceptor = productServiceUserTokenInterceptor;
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productServiceUserTokenInterceptor);
    }
}
