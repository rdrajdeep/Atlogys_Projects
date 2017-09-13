package expertchat.apioperation.apiresponse;

// Created by Kishor on 1/23/2017.


import expertchat.report.Report;

import java.util.ArrayList;
import java.util.List;

public class ParseResponse  implements HTTPCode{

    private ApiResponse response;

    public ParseResponse(ApiResponse response){

        this.response=response;
    }



    public String getJsonData(String keys, ResponseDataType type){

        switch (type.ordinal()){

            case 0:     return response.getResponse().jsonPath().getString(keys);

            case 1:     return String.valueOf(response.getResponse().jsonPath().getInt(keys));

            case 2:     return String.valueOf(response.getResponse().jsonPath().getDouble(keys));

            case 3:     return String.valueOf(response.getResponse().jsonPath().getFloat(keys));

            case 4:     return String.valueOf(response.getResponse().jsonPath().getChar(keys));

        }
        return null;
    }

    /**
     *
     * @return
     */
    public String printError(){

        return this.getJsonData("errors", ResponseDataType.STRING);
    }

    /**
     *
     * @return
     */
    public String printSuccess(){

        return this.getJsonData("metadata.message", ResponseDataType.STRING);
    }

    /**
     *
     * @param status
     * @return
     */
    public String  validate(String status) {

        if(status.contains("success")){

            return this.getJsonData("metadata", ResponseDataType.STRING);

        }else if(status.contains("fail")){

            return this.getJsonData("errors", ResponseDataType.STRING);

        }else{
            return this.getJsonData("results", ResponseDataType.STRING);
        }
    }

    /**
     * @return void
     */
    public void printResponseOnConsole(){

        response.getResponse().prettyPrint();
    }

    public String [] writeResponseToLog(String apiName){

       String [] responseAsString={

               apiName,response.getResponse().getBody().prettyPrint()};

       return responseAsString;
    }

    /**
     *
     * @return HTTP status code
     */
    public int serverStatusCode(){

        return response.getResponse().statusCode();
    }

    /**
     *
     * @return
     */
    public String getVerificationCode(){

        return getJsonData("results.verification_code", ResponseDataType.STRING);
    }


    public List<String> getNonFieldError(){

        List<String> nonFieldError=new ArrayList<>();

        nonFieldError.add(getJsonData("errors.non_field_errors[0].message",ResponseDataType.STRING));

        nonFieldError.add(String.valueOf(getJsonData("errors.non_field_errors[0].code",ResponseDataType.INT)));

        return nonFieldError;

    }


    public boolean getNotFoundError(){

        String error=getJsonData("errors.detail",ResponseDataType.STRING);
        if(error.equals("Not found.")){

            return true;
        }else {

            return false;
        }
    }
}


