package az.code.carlada.configs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static String getConfigProperty(String property){
        Properties prop = new Properties();
        String fileName = "/home/shafig/Code/1506Proj/CarladaProject/src/main/resources/app.config";
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