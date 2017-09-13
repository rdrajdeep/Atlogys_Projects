//package expertchat.bdd;
//
//// Created by Kishor on 2/21/2017.
//
//import com.relevantcodes.extentreports.ExtentReports;
//import expertchat.apioperation.apiresponse.ApiResponse;
//import expertchat.apioperation.apiresponse.ParseResponse;
//import expertchat.bussinesslogic.ExpertChatApi;
//import expertchat.bussinesslogic.ExpertProfile;
//import expertchat.bussinesslogic.SocialLinks;
//import expertchat.usermap.TestUserMap;
//import expertchat.util.ResponseLogger;
//import org.jbehave.core.annotations.Named;
//import org.jbehave.core.annotations.Pending;
//import org.jbehave.core.annotations.Then;
//import org.jbehave.core.annotations.When;
//
//
//public class Social extends AbstractSteps{
//
//
//    public Social(ExtentReports reports, String casName) {
//
//        super(reports, casName);
//    }
//
//    private SocialLinks    socialLinks=new SocialLinks();
//
//    private ApiResponse    response=ApiResponse.getObject();
//
//    private ParseResponse  jsonParser=new ParseResponse(response);
//
//    private ResponseLogger responseLogger=new ResponseLogger(jsonParser);
//
//    private ExpertProfile  expertProfile=new ExpertProfile();
//
//    private ExpertChatApi  expertChatApi = new ExpertChatApi();
//
//
//    /*Social Links Test Cases*/
//    @When("login with $user")
//    @Then("login with $user")
//    public void login(@Named("user") String user) {
//
//        info("Login");
//
//        if (user.contains("{")) {
//            expertChatApi.doLogIn(user);
//        } else {
//            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user));
//        }
//
//        jsonParser.printResponseOnConsole();
//        jsonParser.checkAndWriteToReport(jsonParser.serverStatusCode(),"Login test passed", this);
//    }
//
//    @When("post social links from $social")
//    @Then("post social links from $social")
//    @Pending
//    public void postSocialLink(@Named("social") String social){
//
//        this.info("Posting Social Links..");
//        socialLinks.postSocialLink(social);
//        jsonParser.printResponseOnConsole();
//        jsonParser.checkAndWriteToReport(jsonParser.serverStatusCode(),
//                "Social link posted", this);
//        responseLogger.writeResponseAsLog("Post Social Link");
//    }
//
//    @Then("get the social links")
//    public void getSocialLinks(){
//
//        this.info("Getting Social links ");
//        socialLinks.getSocialLinks();
//        jsonParser.printResponseOnConsole();
//        jsonParser.checkAndWriteToReport(jsonParser.serverStatusCode(),
//                "Social link get Successful", this);
//        responseLogger.writeResponseAsLog("Get Social Link");
//    }
//
//
//    @Then("add social link to expert profile")
//    public void addSocialLinkToExpertProfile(){
//
//        this.info("Adding social link to expert profile");
//        socialLinks.addLinksToExpertProfile(expertProfile.getExpertID());
//        jsonParser.printResponseOnConsole();
//        jsonParser.checkAndWriteToReport(jsonParser.serverStatusCode(),
//                "Social link added to expert profile->"+expertProfile.getExpertID(), this);
//        responseLogger.writeResponseAsLog("Add social link to expert profile");
//    }
//
//
//    @Then("list all linked links for ExpertProfile")
//    public void getLinkedLinks(){
//
//        this.info("Getting social links linked to expert profile");
//        socialLinks.getLinkedListsOfExpertProfile(expertProfile.getExpertID());
//        jsonParser.printResponseOnConsole();
//        jsonParser.checkAndWriteToReport(jsonParser.serverStatusCode(),
//                "Successfully get all links->"+expertProfile.getExpertID(), this);
//        responseLogger.writeResponseAsLog("Get social link from expert profile");
//    }
//
//
//    @Then("add RSS Feeds to social Link url $url")
//    public void addFeed(@Named("url") String url){
//
//        this.info("Adding RSS feed ");
//        socialLinks.addFeedsToSocialLink(url);
//        jsonParser.printResponseOnConsole();
//        jsonParser.checkAndWriteToReport(jsonParser.serverStatusCode(),
//                "Successfully added RSS feed->"+expertProfile.getExpertID(), this);
//        responseLogger.writeResponseAsLog("Add RSS feed");
//    }
//
//    @Then("remove social link")
//    public void removeSocialLink(){
//
//        this.info("Removing social link");
//        boolean isDelete=socialLinks.deleteSocialLink(socialLinks.getSocialLinkId());
//        jsonParser.AssertAndWriteToReport(isDelete,
//                "Successfully removed social links",this);
//        responseLogger.writeResponseAsLog("Remove social link");
//    }
//}
