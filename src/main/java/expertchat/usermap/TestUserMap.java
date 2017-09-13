package expertchat.usermap;

// Created by Kishor on 1/25/2017.

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class TestUserMap {

    private static  Map<String , String>userMap=new LinkedHashMap<>();

    public static void setTestData(String key, String user){

        userMap.put(key, user);
    }

    public static String getUserCredentialsByKey(String key){

        return userMap.get(key);
    }

    public static String getEmailOf(String user){

        JsonObject jsonObject= (JsonObject) new JsonParser().parse(getUserCredentialsByKey(user));
        return jsonObject.get("email").getAsString();

    }

    public static String getPasswordOf(String user){

        JsonObject jsonObject= (JsonObject) new JsonParser().parse(getUserCredentialsByKey(user));
        return jsonObject.get("password").getAsString();

    }

    public static  Map<String, String> getMap(){

        return userMap;
    }

}
