package me.luandevguerra.rentacar.api.utils;

import java.util.HashMap;

public class IntegrationContextData {
    private static HashMap<Object, Object> data = new HashMap<>();

    public static void resetData() {
        data.clear();
    }

    public static void setContextData(String key, Object value) {
        data.put(key, value);
    }

    public static Object getContextData(String key) {
        return data.get(key);
    }

}
