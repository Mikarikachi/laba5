package ru.larina;

import ru.larina.annotation.AutoInjectable;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Injector {

    /**
     * Inject into objects fields with annotation AutoInjectable new objects of defined type from properties.
     *
     * @param tClass  any type object
     * @return same object with initialize fields
     */
    public static <T> T inject(T tClass) throws ReflectiveOperationException{
        List<Field> fields = List.of(tClass.getClass().getDeclaredFields());
        Properties properties = getPropertiesByPath("src/main/resources/config.properties");

        for(Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String type = field.getAnnotatedType().toString();
                String implType = properties.get(type).toString();
                Object value = Class.forName(implType).getConstructor().newInstance();
                setFieldValue(tClass, field.getName(), value);
            }
        }
        return tClass;
    }

    private static Properties getPropertiesByPath(String path) {
        Properties property = new Properties();

        try (FileInputStream fis = new FileInputStream(path)) {
            property.load(fis);
            return property;
        } catch (IOException e) {
            System.out.println("ОШИБКА: Файл свойств отсуствует!");
            throw new IllegalArgumentException(e);
        }
    }

    private static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);

        field.setAccessible(true);
        field.set(object, value);
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        Bean bean = inject(new Bean());
        bean.foo();
    }
}