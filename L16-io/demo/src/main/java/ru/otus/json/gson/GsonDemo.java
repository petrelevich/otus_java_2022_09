package ru.otus.json.gson;

import com.google.gson.Gson;


public class GsonDemo {
    public static void main(String[] args) {
        var gson = new Gson();
        var obj = new BagOfPrimitives(22, "test", 10);
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);

        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
        System.out.println(obj.equals(obj2));
        System.out.println(obj2);
    }
}
