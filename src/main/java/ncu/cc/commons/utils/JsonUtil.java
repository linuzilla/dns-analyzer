package ncu.cc.commons.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    private static class JsonIgnoreExclusionStrategies implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(JsonIgnore.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

    private static final JsonIgnoreExclusionStrategies exclusionStrategies = new JsonIgnoreExclusionStrategies();

    public static Gson gsonExcludeJsonIgnore() {
        return new GsonBuilder().setExclusionStrategies(exclusionStrategies).create();
    }
}
