package expertchat.util;

// Created by Kishor on 1/24/2017.

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public final class JsonBuilder {

    Gson gson=new Gson();

    String oldJson=null;
    String newJson=null;

    public String getJson(){

        return newJson;
    }

    public void setJson(String fileName , String ...jsonValues){

        try {

            if(fileName.equals("") || fileName==null|| !ExpertChatUtility.keySet().contains(fileName)){

                throw new ExpertChatException
                        ("Invalid file name provided.Please refer to your properties file");
            }

            oldJson= FileUtils.readFileToString(new File(ExpertChatUtility.getValue(fileName)),
                    "UTF-8");

        }catch (IOException exception){

            exception.printStackTrace();
        }

        switch (fileName){

            case "register":registrationJson(jsonValues); break;

            case "login":loginJson(jsonValues); break;

            default: throw new ExpertChatException("Invalid file name provided.Please refer to your properties file");
        }
    }

    /**
     *
     * @param jsonValues
     */
    private void loginJson(String[] jsonValues) {

        ExpertChatUtility.nullCheker(jsonValues);

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(oldJson);
        jsonObject.remove("email");
        jsonObject.remove("password");
        jsonObject.addProperty("email",jsonValues[0]);
        jsonObject.addProperty("password",jsonValues[1]);

        newJson=gson.toJson(jsonObject).toString();
    }

    /**
     *
     * @param jsonValues
     */
    private void registrationJson(String[] jsonValues) {

        ExpertChatUtility.nullCheker(jsonValues);

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(oldJson);
        jsonObject.remove("email");
        jsonObject.remove("password");
        jsonObject.addProperty("email",jsonValues[0]);
        jsonObject.addProperty("password",jsonValues[1]);

        newJson=gson.toJson(jsonObject).toString();
    }

}
