package expertchat.bussinesslogic;

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.report.Report;
import expertchat.util.ExpertChatException;
import java.util.concurrent.Callable;
import static expertchat.apioperation.ExpertChatEndPoints.SESSION;
import static expertchat.apioperation.ExpertChatEndPoints.SOCIAL;

/**
 * @Class contains methods to drive the calling APIs
 * Inherits AbstractApiFactory to use POST PUT GET DELETE methods
 */
public class Calling extends AbstractApiFactory {

    private ApiResponse response = ApiResponse.getObject();

    private ParseResponse parseResponse = new ParseResponse(response);

    private SessionManagement session = SessionManagement.session();

    private String id;

    public String getId() {
        return id;
    }

    /**
     *
     */
    public String getStatusOfCall(String key) {

        return parseResponse.getJsonData(key, ResponseDataType.INT);
    }

    public void doCall(String json) {

        response.setResponse(

                this.post(json, SESSION, session.getToken(),true)
        );

        response.printResponse();

        if (response.statusCode() == HTTPCode.HTTP_ACCEPTED) {

            id = parseResponse.getJsonData("results.id", ResponseDataType.STRING);

        } else {

            throw new ExpertChatException("Server Error-(Calling.doCall):->" + response.statusCode());
        }
    }

    public boolean isAcceptCall() {

        String url = SESSION + getId() + "/accept/";

        response.setResponse(
                this.post("", url, session.getToken(),true)
        );

        response.printResponse();

        if (getStatusOfCall("results.status").equals(CallStatus.ACCEPTED)) {

            return true;
        }
        return false;
    }

    public boolean isDissconnectCall() {

        String url = SESSION + getId()+"/disconnect/";

        response.setResponse(
                this.delete("{\"tokbox_session_length\": 20}", url, session.getToken(),true)
        );

        response.printResponse();

        if (getStatusOfCall("results.status").equals(CallStatus.COMPLETED)) {

            System.out.println(getStatusOfCall("results.status"));
            return true;
        }
        return false;
    }

    public boolean isDecline() {

        String url = SESSION+ getId() + "/decline/";

        response.setResponse(
                this.delete("", url, session.getToken(),true)
        );

        response.printResponse();

        if (getStatusOfCall("results.status").equals(CallStatus.DECLINED)) {

            return true;
        }
        return false;
    }

    public boolean isDelay() {

        String url = SESSION+ getId() + "/delay/";

        response.setResponse(
                this.put("{\"delay_time\":1}", url, session.getToken(),true)
        );

        response.printResponse();

        if (getStatusOfCall("results.status").equals(CallStatus.DELAYED)) {

            return true;
        }
        return false;
    }
}
