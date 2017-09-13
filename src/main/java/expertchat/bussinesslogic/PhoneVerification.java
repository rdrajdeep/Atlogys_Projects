package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.ExpertChatEndPoints;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

/**
 * Created by root on 18/2/17.
 */

public class PhoneVerification extends AbstractApiFactory implements ExpertChatEndPoints{

    ApiResponse response = ApiResponse.getObject();

    ParseResponse jsonParser = new ParseResponse(response);

    SessionManagement session = SessionManagement.session();

    private String code;

    public void phoneCodeSend(String phone){

        response.setResponse(
                this.post(phone,PHONECODESEND,session.getToken())
        );
        response.printResponse();

        if(response.statusCode()==HTTPCode.HTTP_OK){
            setCode(jsonParser.getJsonData("results.verification_code", ResponseDataType.STRING));
        }
    }

    public void phoneCodeResend(String phone){

        response.setResponse(
                this.post(phone, PHONECODERESEND, session.getToken())
        );
        response.printResponse();
        setCode(jsonParser.getJsonData("results.verification_code", ResponseDataType.STRING));

    }

    public void verfiyPhone(String code){
        String json="{\"passcode\":\""+code+"\"}";
        response.setResponse(
                this.post(code,PHONECODEVERIFY, session.getToken())
        );

        response.printResponse();
    }

    private void setCode(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }

    public boolean isCodeSent(){
        if(this.getCode()!=null){
            return true;
        }
        return false;
    }
}
