package expertchat.util;

// Created by Kishor on 1/24/2017.

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Set;

public class ExpertChatUtility {

    private static Properties prop=null;
    private static FileInputStream ins = null;


    private static void setProp(String fileName){
        try {
            ins = new FileInputStream(fileName);

        } catch (IOException e) {

            System.out.print(e.getMessage());
        }
        prop = new Properties();

        try {
            prop.load(ins);

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

    }


    /**
     * @param key
     * @return value based on key presents in the properties file
     */
    public static String getValue(String key) {
        setProp("ExpertChat.Properties");
        return prop.getProperty(key);
    }

    /**
     *
     * @param
     * @return
     */
    public static Set<Object> keySet(){

        setProp("ExpertChat.Properties");
        return prop.keySet();
    }

    public static String directoryName(){

        LocalDateTime localDateTime=LocalDateTime.now();
        String day=String.valueOf(localDateTime.getDayOfMonth());
        String month=String.valueOf(localDateTime.getMonthValue());
        String year=String.valueOf(localDateTime.getYear());
        String seconds=String.valueOf(localDateTime.getSecond());

        return (day+"-"+month+"-"+year+"-"+seconds);
    }

    /**
     * @param key
     * @return value based on key presents in the properties file
     * @Author: Kishor Jyoti Sarma
     */
    public static String getValueFromUserMap(String key) {
        setProp("usermap.properties");
        return prop.getProperty(key);
    }

    /***
     *
     * @param param
     * @return
     * In  our story file params are enclosed with curly braces. But we are not sending those
     * ugly braces to server. call this method to clean anything that is not a word character
     */
    public static String clean(String param){

        return param.replaceAll("[^\\w\\s]","");
    }

    public static void nullCheker(String [] array){

        if(array.length==0){

            throw new ExpertChatException("Empty array. Please provide data");
        }
    }
}

