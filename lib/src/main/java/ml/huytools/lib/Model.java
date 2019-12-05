package ml.huytools.lib;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;

/***
 * Model.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/11/2019
 * Update: 20/11/2019
 *
 */
public class Model {

    public String toJson(){
        JSONObject jsonObject = new JSONObject();

        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            boolean isAnnotationName = field.isAnnotationPresent(JsonName.class);
            if(isAnnotationName){

                Annotation annotation = field.getAnnotation(JsonName.class);
                String name = ((JsonName) annotation).value();
                name = (name == "" ? field.getName() : name);

                Object value = null;
                try {
                    value = field.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                try {
                    jsonObject.put(name, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonObject.toString();
    }


    public static<T extends Model> T ParseJson(Class<T> clazz, String json){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonObject == null)
            return null;

        T model = null;
        try {
            model = clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if(model == null)
            return null;

        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            boolean isAnnotationName = field.isAnnotationPresent(JsonName.class);
            if(isAnnotationName){

                Annotation annotation = field.getAnnotation(JsonName.class);
                String name = ((JsonName) annotation).value();
                name = (name == "" ? field.getName() : name);
                try {
                    field.set(model, jsonObject.get(name));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return model;
    }


}
