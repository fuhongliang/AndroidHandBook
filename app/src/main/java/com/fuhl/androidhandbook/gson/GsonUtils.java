package com.fuhl.androidhandbook.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author tony
 * @date 2018/7/17
 */
public class GsonUtils {
    /**
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json,Class<T> cls){
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }

    public static <T> ArrayList<T> fromJsonArrayToArrayList(String json, Class<T> clazz) throws Exception {
        ArrayList<T> lst = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            lst.add(new Gson().fromJson(elem, clazz));
        }
        return lst;
    }


    public static Gson gson;

    /**
     * 将对象转换成Json格式的字符串
     * @param object 要转换成Json的对象
     * @return String:Json格式的字符串
     */
    public static String convertObject2Json(Object object) {
        gson=new Gson();
        return gson.toJson(object);
    }

    /**
     * 将InputStream封装成BufferedReader
     * @param inputStream
     * @return
     */
    private static BufferedReader intputStream2BufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
