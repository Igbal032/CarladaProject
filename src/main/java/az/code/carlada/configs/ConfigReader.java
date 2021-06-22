package az.code.carlada.configs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static String getConfigProperty(String property){
        Properties prop = new Properties();
        String fileName = "app.config";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {

        }
        try {
            prop.load(inputStream);
        } catch (IOException ex) {

        }
        return prop.getProperty(property);
    }
}