package expertchat.bussinesslogic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;

import static expertchat.apioperation.ExpertChatEndPoints.EXPERT_PROFILE;

public class ExpertProfile extends AbstractApiFactory {

    private ApiResponse response = ApiResponse.getObject();

    private ParseResponse parseResponse = new ParseResponse(response);

    private SessionManagement session = SessionManagement.session();

    private FileUpload fileUpload=new FileUpload();

    private String expertID;

    private String expertCredential;

    public void setExpertID(String expertID) {
        this.expertID = expertID;
    }

    public String getExpertID() {
        return expertID;
    }

    /**
     * @param profile
     */
    public void addExpertyProfile(String profile) {

        response.setResponse(
                this.post(profile, EXPERT_PROFILE, session.getToken())
        );

        response.printResponse();
    }

    /**
     * @param expertID
     */
    public void getProfileOfExpert(String expertID) {

        response.setResponse(
                this.get(EXPERT_PROFILE + expertID + "/", session.getToken())
        );
        response.printResponse();
    }

    public void getAllProfileOfExpert() {

        response.setResponse(
                this.get(EXPERT_PROFILE, session.getToken())
        );

        response.printResponse();
    }

    public String getProfileCount() {

        return parseResponse.getJsonData("metadata.count", ResponseDataType.INT);
    }

    public void getNextPage() {

        String page = parseResponse.getJsonData("metadata.next", ResponseDataType.STRING);
        response.setResponse(
                this.get(page, session.getToken())
        );

        response.printResponse();
    }

    public void updateExpertProfile(String profile, String id) {

        response.setResponse(
                this.patch(profile, EXPERT_PROFILE + id + "/", session.getToken())
        );

        response.printResponse();
    }

    public boolean deleteProfile(String id) {

        if (this.isDelete(EXPERT_PROFILE + id + "/", session.getToken())) {

            System.out.println("profile deleted");

            this.get(EXPERT_PROFILE + id + "/", session.getToken());

            if (parseResponse.getNotFoundError()) {

                return true;
            }
        }
        return false;
    }

    public void setExpertCredential(String expertCredential){

        this.expertCredential=expertCredential;

    }

    public String [] getExpertCredential(){

        JsonObject jsonObject= (JsonObject) new JsonParser().parse(this.expertCredential);
        String credential[]={jsonObject.get("email").toString(), jsonObject.get("password").toString()};
        return credential;
    }

    public void uploadMedia(String mediaPath){

        fileUpload.uploadMedia(mediaPath,getExpertCredential()[0], getExpertCredential()[1]);
    }

    public String getResponseOfMediaUpload(){

        return FileUpload.getJson();
    }
}
