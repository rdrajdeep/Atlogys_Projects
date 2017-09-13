package expertchat.bussinesslogic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sun.deploy.panel.AbstractRadioPropertyGroup;
import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.util.ExpertChatException;
import expertchat.util.OpenBrowser;
import io.restassured.http.Header;
import io.restassured.mapper.factory.GsonObjectMapperFactory;
import io.restassured.response.Response;
import org.jbehave.core.annotations.Given;

import java.time.Instant;
import java.util.*;

import static expertchat.apioperation.ExpertChatEndPoints.*;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;


public class SocialLinks extends AbstractApiFactory {


    private ApiResponse response=ApiResponse.getObject();

    private ParseResponse parseResponse=new ParseResponse(response);

    private String url;

    private ArrayList<String> socialLinkId=new ArrayList<>();

    private HashMap<String,String > info=new LinkedHashMap<>();

    public String getUrl(){

        return url;
    }


     private void setResponse(String social){
         response.setResponse(

                 this.get("http://api.qa.experchat.com/v1/social/"+social+"/", SessionManagement.session().getToken())
         );
         response.printResponse();

         url=parseResponse.getJsonData("results.url", ResponseDataType.STRING);
         System.out.println("Insta-->"+url);

     }

    private void GetTheURLforSocialLinkDevices(String social){

          switch (social.toLowerCase()){

              case "instagram": setResponse("instagram"); break;

              case "facebook":  setResponse("facebook");  break;

              case "youtube":   setResponse("youtube");   break;

              default: throw new ExpertChatException("Invalid Social media");

          }
    }

    /**
     *
     * @param social
     */
    private void getTheInfoOfUser(String social){

     //   String code=new OpenBrowser().getCodeFromBrowser(this.getUrl(),social);

      //  String url="http://api.qa.experchat.com/v1/social/"+social+"/get_token/"+code+"/";

        System.out.println("Code url-->"+url);

        response.setResponse(
                this.get(url,SessionManagement.session().getToken())
        );

        response.printResponse();

        if(social.toLowerCase().equals("instagram")){

            info.put("instaUserID",parseResponse.getJsonData("results[0].id",ResponseDataType.STRING));

            info.put("instaAccount",parseResponse.getJsonData("results.metadata.id",ResponseDataType.INT));

        } else

            if(social.toLowerCase().equals("facebook")){

            info.put("fbUserID",parseResponse.getJsonData("results[0].id",ResponseDataType.STRING));

            info.put("fbPageID",parseResponse.getJsonData("results[1].id",ResponseDataType.STRING));

            info.put("fbAccount",parseResponse.getJsonData("results.metadata.id",ResponseDataType.INT));
        }

    }


    public void postSocialLink(String socialMedia){

        GetTheURLforSocialLinkDevices(socialMedia);
        getTheInfoOfUser(socialMedia);

        int account;
        String detail;
        String json=null;

        switch (socialMedia.toLowerCase()){

            case "instagram": account=Integer.parseInt(info.get("instaAccount"));
                              detail=info.get("instaUserID");
                              json="{\"feed_type\":2,\"account\":"+account+",\"detail\": \""+detail+"\"}";

                              break;

            case "facebook-user" : account=Integer.parseInt(info.get("fbAccount"));
                                   detail=info.get("fbUserID");
                                   json="{\"feed_type\":1,\"account\":"+account+",\"detail\": \""+detail+"\"}";

                                   break;

            case "facebook-page" : account=Integer.parseInt(info.get("fbAccount"));
                                   detail=info.get("fbPageID");
                                   json="{\"feed_type\":1,\"account\":"+account+",\"detail\": \""+detail+"\"}";

                                   break;

        }

        response.setResponse(
                this.post(json, SOCIAL+SOCIAL_LINKS, SessionManagement.session().getToken())
        );

        response.printResponse();
    }


    public void getSocialLinks(){

        response.setResponse(
                this.get(SOCIAL+SOCIAL_LINKS, SessionManagement.session().getToken())
        );

        response.printResponse();

        List s=response.getResponse().jsonPath().getList("results");

        for(int i=0; i<s.size();i++){

            int index1=s.get(i).toString().indexOf("id");
            int index2=s.get(i).toString().indexOf("detail");
            socialLinkId.add(s.get(i).toString().substring(index1+3,index2).replace(",",""));
        }

    }

    public List<String> getSocialLinkId(){

        return socialLinkId;
    }

    public boolean deleteSocialLink( String id){

        return this.isDelete(SOCIAL+SOCIAL_LINKS+id+"/", SessionManagement.session().getToken());
    }


   public void addLinksToExpertProfile( String expertId) {

       int size=socialLinkId.size();

       String s="";

       for(int i=0;i<size;i++){

           s=s+socialLinkId.get(i)+",";
       }

       String mString=s.replaceFirst(".$", "");

       String json = "{\n" +
               "   \"social_link_ids\":  [\n" +
               "   " +mString+" \n" +
               "   \t]\n" +
               "}";

       System.out.println("Here is the JSON "+json);

       String url = EXPERT_PROFILE + expertId + "/update_social_links/";

       response.setResponse(

               this.put(json,url,SessionManagement.session().getToken())
       );

       response.printResponse();
   }

   public void getLinkedListsOfExpertProfile( String expertID){

        String url=PROFILE+expertID+"/"+SOCIAL_LINKS;

        response.setResponse(

                this.get(url,SessionManagement.session().getToken())
        );

        response.printResponse();

   }



    public void addFeedsToSocialLink(String rssFeedUrl) {

       response.setResponse(

               this.post(rssFeedUrl, FEED_LINK, SessionManagement.session().getToken())
       );

       response.printResponse();

    }

    public String getSocialLinkCount(){

       return parseResponse.getJsonData("metadata.count", ResponseDataType.INT);

    }

    public void getFeedListing() {

        long unixTimeStamps= Instant.now().getEpochSecond()/1000L;

        response.setResponse(

                this.get(GET_FEEDS + unixTimeStamps, SessionManagement.session().getToken())
        );

        response.printResponse();
    }

    public void publishContent(String contentID){

       String json="{\"content_id\":\""+contentID+"\"}";

       response.setResponse(

               this.post(json, PUBLISH_CONTENT, SessionManagement.session().getToken())
       );

       response.printResponse();
    }

    public void publishContentAs(String contentSource){

       // String json="{\"content_id\":\""+contentID+"\"}";

        long unixTimeStamps= Instant.now().getEpochSecond()/1000L;

        // Response r = this.get(GET_FEEDS + unixTimeStamps, "token eyJ1c2VyX2lkIjozMDQsInRpbWVzdGFtcCI6MTQ4ODM3MTI1MC4xMTMwODQsImlwX2FkZHJlc3MiOiI2MS4yNDYuNDcuOTMifQ:1cj3Lu:vlBn2bcaUA7JUNnEi1q0JW6Jl8A");

         Header header=new Header("authorization","token eyJ1c2VyX2lkIjozMDQsInRpbWVzdGFtcCI6MTQ4ODM3MzI0OS40NzczMTcsImlwX2FkZHJlc3MiOiI2MS4yNDYuNDcuOTMifQ:1cj3s9:mwW7u_P3g2-JV-R9aVTC47Vu0_Y");

         Response r1=given().header(header).get(GET_FEEDS + unixTimeStamps);

         r1.prettyPrint();

         List list=r1.getBody().jsonPath().getList("results");

         System.out.println(list.get(1));

        if(contentSource.toLowerCase().equals("facebook")){

            int type=1;

        }
//        response.setResponse(
//
//                this.post(json, PUBLISH_CONTENT, SessionManagement.session().getToken())
//        );
    }



}
