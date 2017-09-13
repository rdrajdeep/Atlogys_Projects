package expertchat.bussinesslogic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.usermap.TestUserMap;
import static expertchat.apioperation.ExpertChatEndPoints.CHANGE_PASSWORD;
import static expertchat.apioperation.ExpertChatEndPoints.RESEND_EMAIL_VERIFICATION;


public class ExpertChatApi extends AbstractApiFactory{

    private  ApiResponse response=ApiResponse.getObject();

    private ParseResponse jsonParser=new ParseResponse(response);

    /**
     *
     * @param json
     */
    public void doRegistration(String json){

        System.out.println("REGISTRATION");
        response.setResponse(
                this.post(json, ExpertChatEndPoints.REGISTER)
        );

        EmailVerification.setVerificationEndPoint(jsonParser.getVerificationCode());

        response.printResponse();
    }

    /**
     *
     * @param json
     */

    public void doLogIn(String json){

        System.out.println("LOGIN");
        response.setResponse(
                this.post(json, ExpertChatEndPoints.LOGIN)
        );

        SessionManagement.session().setToken(
                jsonParser.getJsonData("results.token", ResponseDataType.STRING)
        );

        response.printResponse();

    }

    public void verifyUser(){

        System.out.println("VERIFY USER");
        response.setResponse(
                this.get(EmailVerification.getVerificationEndPoint())
        );

        response.printResponse();
    }

    /**
     *
     * @param password
     * @param user
     */
    public void changePassword(String password, String user) {

        System.out.println("CHANGE PASSWORD");
        String json="{\"current_password\":\"jyoti1032\",  \"new_password\":\"1032kishor\"}";

        JsonObject jsonObject=(JsonObject)new JsonParser().parse(json);
        jsonObject.remove("current_password");
        jsonObject.addProperty("current_password",TestUserMap.getPasswordOf(user));
        jsonObject.remove("new_password");
        jsonObject.addProperty("new_password",password);
        response.setResponse(
                this.post(jsonObject.toString(), CHANGE_PASSWORD, SessionManagement.session().getToken()));

        response.printResponse();

    }

    /**
     *
     * @param password
     */
    public void resetPassword(String password) {

        System.out.println("Oh Yeah..!!!!");

    }

    public void resendEmailVerification(String json) {

        System.out.println("RESEND EMAIL VERIFICATION");
        response.setResponse(
                this.post(json,RESEND_EMAIL_VERIFICATION)
        );

        response.printResponse();

    }
}
