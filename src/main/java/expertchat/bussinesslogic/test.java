package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ApiFactories;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ParseResponse;

import javax.xml.ws.Response;

public class test extends AbstractApiFactory {

    ApiResponse response= ApiResponse.getObject();

    ParseResponse parse=new ParseResponse(response);

    public void sendaPost(String json){

        response.setResponse(

                this.post(json, "url", "token")
        );

        if(response.getResponse().statusCode()==200){


        }

        response.getResponse().jsonPath().get("data.id");


    }


}
