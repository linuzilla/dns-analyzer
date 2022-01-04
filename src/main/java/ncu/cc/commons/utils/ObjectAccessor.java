package ncu.cc.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ObjectAccessor<T> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectAccessor.class);

    private final T object;
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public ObjectAccessor(T object) {
        this.object = object;
        this.clazz = (Class<T>) object.getClass();
    }

    public boolean set(String fieldName, Object value) throws IllegalAccessException {
        logger.debug("set {} to {}.{}", value, this.clazz.getSimpleName(), fieldName);
        Class<?> clazz = this.clazz;

        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(this.object, value);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <V> V get(String fieldName, Class<V> retrieveType) throws IllegalAccessException {
        Class<?> clazz = this.clazz;

        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (V) field.get(this.object);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}
