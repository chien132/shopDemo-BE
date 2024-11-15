package chien.demo.shopdemo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Utilities.
 */
public final class Utilities {
    private Utilities() {}

    /**
     * parseString.
     */
    public static String parseString(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convert camel case to uppercase separated string.
     *
     * @param input input string
     * @return the formatted string
     */
    public static String convertCamelCaseToUppercaseSeparated(String input) {
        // Use a regular expression to find the start of each word and add a space before it
        String result = input.replaceAll("([a-z])([A-Z])", "$1 $2");
        return result.toUpperCase();
    }


    /**
     * Convert camelCase to snake_case string.
     *
     * @param str the str
     * @return the string
     */
    public static String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Convert String to camel case.
     *
     * @param input the input string
     * @return the formatted string
     */
    public static String toCamelCaseString(String input) {
        input = input.replace("_", " ");
        String[] parts = input.split("\\s+");
        StringBuilder camelCaseString = new StringBuilder(parts[0].toLowerCase());

        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
        }

        return camelCaseString.toString();
    }

    /**
     * Converts a camelCase string to a more readable format.
     *
     * @param input the camelCase string to be converted
     * @return a String representing the converted readable format
     */
    public static String convertToReadableFormat(String input) {
        String[] words = input.split("(?=[A-Z])");

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return result.toString().trim();
    }

    /**
     * Checks whether all declared inner classes of the specified class are enums both primitive enum and customize
     * enum.
     *
     * @param clazz the class whose inner classes are to be checked
     * @return {@code true} if all declared inner classes are enums, {@code false} otherwise
     */
    public static boolean isEnum(Class<?> clazz) {
        if (!clazz.isEnum()) {
            for (Field fieldType : clazz.getDeclaredFields()) {
                if (!fieldType.getType().isEnum()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves the enum constants of the specified class as a list of maps.
     * If the class is a primitive enum, it uses {@link #getPrimitiveEnumValuesAsMap(Class)};
     * else it is a customized enum, it uses {@link #getCustomEnumValuesAsMap(Class)}.
     *
     * @param clazz the class type of the enum to process
     * @return a list of maps representing the enum values and names
     * @throws NoSuchMethodException if the method to retrieve enum values is not found
     */
    public static List<Object> getEnumValuesAsMap(Class<?> clazz) throws NoSuchMethodException {
        if (clazz.isEnum()) {
            return getPrimitiveEnumValuesAsMap(clazz);
        } else {
            return getCustomEnumValuesAsMap(clazz);
        }
    }

    /**
     * Retrieves the primitive enum constants of the given enum class as a list of maps.
     * Each map contains two entries: "value" (the enum constant itself) and "name" (the name of the enum constant).
     * This function works with any enum class and returns the enum constants in a structured format.
     *
     * @param clazz the enum class whose constants are to be processed
     * @return a list of maps, where each map contains "value" (the enum constant) and "name" (the enum constant's name)
     * @throws IllegalArgumentException if the provided class is not an enum class
     */
    private static List<Object> getPrimitiveEnumValuesAsMap(Class<?> clazz) throws NoSuchMethodException {
        List<Object> result = new ArrayList<>();
        List<? extends Enum<?>> primitiveEnum =
                Arrays.stream(clazz.getEnumConstants()).map(e -> e instanceof Enum<?> ? (Enum<?>) e : null).toList();
        for (Enum<?> e : primitiveEnum) {
            Map<String, Object> enumValuesMap = new HashMap<>();
            enumValuesMap.put("value", e.getClass().getMethod("getValue"));
            enumValuesMap.put("name", e.name());
            result.add(enumValuesMap);
        }
        return result;
    }

    /**
     * Retrieves the values and names of a custom enum class as a list of maps, where each map contains
     * the fields "name" and "value". The custom enum is expected to have only two fields: "name" and "value".
     * Each of these fields should be of an enum type.
     *
     * @param clazz the enum class that contains the "name" and "value" fields
     * @return a list of maps, where each map has keys "name" and "value", representing the corresponding enum values
     * @throws IllegalArgumentException if the enum class doesn't contain exactly two fields "name" and "value",
     *                                  or if the sizes of the two arrays do not match
     * @throws NoSuchElementException   if the "name" or "value" field is not found in the enum class
     */
    private static List<Object> getCustomEnumValuesAsMap(Class<?> clazz) {
        List<Object> result = new ArrayList<>();

        // Find the field named "value"
        Field fieldValue =
                Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getName().equals("value")).findFirst()
                        .orElseThrow(() -> new NoSuchElementException("Field 'value' not found"));

        // Find the field named "name"
        Field fieldName =
                Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getName().equals("name")).findFirst()
                        .orElseThrow(() -> new NoSuchElementException("Field 'name' not found"));

        // Get enum constants for both fields
        List<? extends Enum<?>> values = Arrays.stream(fieldValue.getType().getEnumConstants())
                .map(enumz -> enumz instanceof Enum<?> ? (Enum<?>) enumz : null).toList();
        List<? extends Enum<?>> names = Arrays.stream(fieldName.getType().getEnumConstants())
                .map(enumz -> enumz instanceof Enum<?> ? (Enum<?>) enumz : null).toList();

        // Ensure that both arrays have the same length
        if (values.size() != names.size()) {
            throw new IllegalArgumentException("Each object of enums must have the same length");
        }

        // Map the "name" and "value" fields into a list of maps
        for (int i = 0; i < values.size(); i++) {
            Map<String, Object> enumValuesMap = new HashMap<>();
            if (values.get(i) instanceof Enum<?> valueEnum) {
                enumValuesMap.put("value", valueEnum);
            }
            if (names.get(i) instanceof Enum<?> nameEnum) {
                enumValuesMap.put("name", nameEnum);
            }
            result.add(enumValuesMap);
        }

        return result;
    }
}