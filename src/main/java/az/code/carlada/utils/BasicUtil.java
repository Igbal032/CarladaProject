package az.code.carlada.utils;

import az.code.carlada.exceptions.EnumValueNotFound;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class BasicUtil {

    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        try {
            return Enum.valueOf(c, string);
        } catch (IllegalArgumentException ex) {
            throw new EnumValueNotFound(string + " enum value couldn't found in -> " + c.getSimpleName() + " enum");
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createSlug(String content){
        content.replace('@','-')
                .replace('.','-');
        return content;
    }
}