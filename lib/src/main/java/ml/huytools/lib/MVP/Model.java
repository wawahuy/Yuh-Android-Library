package ml.huytools.lib.MVP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import ml.huytools.lib.Annotation.JsonName;


/***
 * Model.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/11/2019
 * Update: 20/11/2019
 *
 */
public class Model<T extends Model> {

    public String toJson(){
        JSONObject jsonObject = new JSONObject();

        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            boolean isAnnotationName = field.isAnnotationPresent(JsonName.class);
            if(isAnnotationName){

                JsonName annotation = (JsonName)field.getAnnotation(JsonName.class);
                String name = annotation.value();
                name = (name == "" ? field.getName() : name);

                Object value = null;
                try {
                    switch(annotation.type()){
                        case Model:
                            value = new JSONObject(((Model)field.get(this)).toJson());

                        case ModelManager:
                            value = new JSONArray(((ModelManager)field.get(this)).toJson());
                            break;

                        default:
                            value = field.get(this);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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


    public static<T extends Model> T ParseJson(Class<T> clazz, String json) {
        T model = null;
        try {
            model = clazz.newInstance();
            model.set(clazz, json);
            return model;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void set(Class<T> clazz, String json){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonObject == null)
            return;


        /// Lấy tấc cả field trong class
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){

            /// Field bao gồm cả private
            field.setAccessible(true);

            /// Kiểm tra field có Annotation JsonName hay không
            boolean isAnnotationName = field.isAnnotationPresent(JsonName.class);
            if(isAnnotationName){

                JsonName annotation = (JsonName)field.getAnnotation(JsonName.class);
                String name = annotation.value();

                /// Khi Annonation không được định nghĩa value thì lấy tên field thay thế
                name = (name == "" ? field.getName() : name);
                try {
                    if(!jsonObject.has(name)){
                        continue;
                    }


                    switch(annotation.type()){
                        case Model:
                            /// Chuyển sang Model
                            /// field.set(model, Model.ParseJson(type, jsonObject.get(name).toString()));
                            Model o = (Model) field.getType().newInstance();
                            o.set(annotation.clazz(), jsonObject.get(name).toString());
                            field.set(this, o);
                            break;

                        case ModelManager:
                            /// Chuyển sang model manager
                            ModelManager mo =  (ModelManager) field.getType().newInstance();
                            mo.set(annotation.clazz(), (JSONArray) jsonObject.get(name));
                            field.set(this, mo);
                            break;

                            default:
                                Class<?> type = field.getType();

                                /// float
                                /// JsonObject không có float nên cần ép kiểu Double sàn Float
                                if(type.isAssignableFrom(float.class)){
                                    field.setFloat(this, (float) jsonObject.getDouble(name));
                                } else {
                                    field.set(this, jsonObject.get(name));
                                }
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
