package az.code.carlada.utils;

import az.code.carlada.exceptions.EnumValueNotFound;

public class BasicUtil {

    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        try {
            return Enum.valueOf(c, string.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new EnumValueNotFound(string + " enum value couldn't found in -> " + c.getSimpleName() + " enum");
        }
    }
}