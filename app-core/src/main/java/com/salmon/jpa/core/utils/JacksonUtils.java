package com.salmon.jpa.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salmon.jpa.core.json.HibernateAwareObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonUtils {

    private static ObjectMapper objectMapper = null;

    private static ObjectMapper getInstance() {
        if(objectMapper == null){
            objectMapper = new HibernateAwareObjectMapper();
        }
        return objectMapper;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) {
        try {
            return getInstance().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) {
        try {
            return getInstance().readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json string convert to map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2map(String jsonStr){
        try {
            return getInstance().readValue(jsonStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) {
        try {
            Map<String, Map<String, Object>> map = getInstance().readValue(jsonStr, new TypeReference<Map<String, T>>() {});
            Map<String, T> result = new HashMap<String, T>();
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
        try {
            List<Map<String, Object>> list = getInstance().readValue(jsonArrayStr, new TypeReference<List<T>>() {});
            List<T> result = new ArrayList<T>();
            for (Map<String, Object> map : list) {
                result.add(map2pojo(map, clazz));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return getInstance().convertValue(map, clazz);
    }

}
