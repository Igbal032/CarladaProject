package az.code.carlada.components;

import az.code.carlada.exceptions.FileReadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Component
public class ReaderComponent {
    @Value("${mail.data.url}")
    Resource resource;

    public String property(String key) {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(resource.getFile()));
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }
        return prop.getProperty(key);
    }
}
