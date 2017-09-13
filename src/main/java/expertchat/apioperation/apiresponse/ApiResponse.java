package expertchat.apioperation.apiresponse;

// Created by Kishor on 1/23/2017.

import io.restassured.response.Response;

public final class ApiResponse {

    private ApiResponse(){}

    public static ApiResponse getObject(){

        return responseObj;
    }

     private final static ApiResponse responseObj=new ApiResponse();

     public static String VERIFICATION_CODE;

     private Response response;

     public void setResponse(Response response){

          this.response=response;
     }

     public Response getResponse(){

          return response;
     }

     public void printResponse(){

         getResponse().prettyPrint();
     }

     public int statusCode(){

         return getResponse().statusCode();
     }
}
