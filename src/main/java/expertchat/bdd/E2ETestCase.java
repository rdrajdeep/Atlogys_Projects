package expertchat.bdd;

import com.relevantcodes.extentreports.ExtentReports;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.bussinesslogic.*;
import expertchat.usermap.TestUserMap;
import expertchat.util.ExpertChatException;
import expertchat.util.ResponseLogger;
import org.jbehave.core.annotations.*;
import java.io.IOException;


public class E2ETestCase extends AbstractSteps {


    public E2ETestCase(ExtentReports reports, String casName) {

        super(reports, casName);
    }

    ExpertChatApi expertChatApi = new ExpertChatApi();

    ApiResponse response = ApiResponse.getObject();

    ParseResponse jsonParser = new ParseResponse(response);

    ResponseLogger responseLogger = new ResponseLogger(jsonParser);

    ExpertProfile expertProfile = new ExpertProfile();

    BasicProfile basicProfile = new BasicProfile();

    Calling call = new Calling();

    PhoneVerification phone = new PhoneVerification();

    SocialLinks socialLinks = new SocialLinks();

    /**
     * @param json
     * @param user
     */
    @When("register expert with $json as $user")
    public void register(@Named("json") String json,
                         @Named("user") String user) {

        info("Registration");
        expertChatApi.doRegistration(json);
        this.checkAndWriteToReport(response.statusCode(), "Registration Done");
        TestUserMap.setTestData(user, json);
        responseLogger.writeResponseAsLog("Registration");
    }


    /**
     * Email verification
     */
    @Then("Verify Email")
    public void verifyUser() {

        info("Verify email Address");
        expertChatApi.verifyUser();
        this.checkAndWriteToReport(response.statusCode(), "Email Verified");

    }

    /**
     * @param user
     */
    @When("login with $user")
    @Then("login with $user")
    public void login(@Named("user") String user) {

        info("Login");

        if (user.contains("{")) {
            expertChatApi.doLogIn(user);
            expertProfile.setExpertCredential(user);
        } else {
            expertChatApi.doLogIn(TestUserMap.getUserCredentialsByKey(user));
        }

        this.checkAndWriteToReport(response.statusCode(), "Login test passed");
    }

    /**
     * @param state
     * @param password
     * @param user
     */
    @Then("$state password to $password for $user")
    public void change_or_reset_password(@Named("state") String state,
                                         @Named("password") String password,
                                         @Named("user") String user) {

        switch (state.toLowerCase()) {

            case "change":
                this.info("Changing Password");
                expertChatApi.changePassword(password, user);

                this.checkAndWriteToReport(response.statusCode(),
                        "Password has been changed");
                responseLogger.writeResponseAsLog("Change Password");
                break;

            case "reset":
                expertChatApi.resetPassword(password);
                break;

            default:
                throw new ExpertChatException("State name is invalid");
        }
    }


    /**
     * @param code
     */
    @Then("check error code $code")
    public void check_error_code(@Named("code") String code) {

        String eCode = jsonParser.getJsonData("errors.email[0].code", ResponseDataType.INT);
        this.checkErrorCode(jsonParser.serverStatusCode(), eCode, code);
    }

    /**
     * @param code
     */
    @Then("check non-field error code $code")
    public void check_non_filed_error_code(@Named("code") String code) {

        this.checkNonFiledError(jsonParser.serverStatusCode(),
                jsonParser.getNonFieldError().get(1), code);
    }


    /**
     * @param code
     */
    @Then("check success code $code")
    public void check_success_code(@Named("code") String code) {

        String sCode = jsonParser.getJsonData("metadata.code", ResponseDataType.INT);
        this.checkSuccessCode(response.statusCode(), sCode, code);
    }


    /**
     * @param json
     */
    @Then("Resend $email for $json")
    public void resendEmailVerification(@Named("json") String json) {

        this.info("Resend Verification Information");

        expertChatApi.resendEmailVerification(json);


        this.checkAndWriteToReport(response.statusCode(),
                "Resend email Verification");

        responseLogger.writeResponseAsLog("Resend Email Verification");
    }

    /*Basic Profile Load Test Cases*/

    /**
     * @param user
     */
    @Then("Load basic profile of $user")
    public void loadBasicProfile(@Named("user") String user) {

        this.info("Load basic profile of -" + user);

        basicProfile.loadBasicProfile();

        this.checkAndWriteToReport(response.statusCode(),
                "Basic profile loaded");

        responseLogger.writeResponseAsLog("My Info Load");

    }

    /**
     * @param user
     */
    @Then("verify email of the profile is same as $user")
    public void verifyEmailOfUser(@Named("user") String user) {

        this.info("Verify a Email of user-" + user);

        this.AssertAndWriteToReport(basicProfile.verifyProfileEmail(user),
                "Email is same as login user");
    }

    /**
     * @param name
     */
    @Then("add name as $name")
    public void addName(@Named("name") String name) {

        this.info("Add name to basic profile");
        basicProfile.addName(name);


        String uName = jsonParser.getJsonData("results.name", ResponseDataType.STRING);

        this.checkAndWriteToReport(response.statusCode(),
                "Name added to profile--" + uName);

        responseLogger.writeResponseAsLog("Add name to basic info");

    }

    @Then("add profile photo as $image")
    public void addPhotoToProfile(@Named("image") String image) {

        this.info("Adding profile image");

        try {
            basicProfile.addProfilePhoto(image);


        } catch (IOException e) {

            this.info(e.getMessage());

        } finally {

            String imageUrl = jsonParser.getJsonData("results.image_url", ResponseDataType.STRING);


            this.checkAndWriteToReport(response.statusCode(),
                    "Profile Image Added--" + imageUrl);

            responseLogger.writeResponseAsLog("Me-Photo");
        }

    }


    @Then("verify profile photo of $user")
    public void verifyProfilePhoto(@Named("user") String user) {

        this.info("Verifying profile photo");

        loadBasicProfile(user);

        String profilePhoto = jsonParser.getJsonData("results.profile_photo", ResponseDataType.STRING);

        this.AssertAndWriteToReport(profilePhoto.contains("png"),
                "Profile Photo Verified");

    }

    /*EXPERT PROFILE TEST CASES*/

    /**
     * @param profile
     */
    @Then("expert should be able to post expert profile as $profile")
    @Alias("Create a new Profile as $profile")
    public void postExpertProfile(@Named("profile") String profile) {

        this.info("Post Expert Profile");

        expertProfile.addExpertyProfile(profile);

        expertProfile.setExpertID(
                jsonParser.getJsonData("results.id", ResponseDataType.INT)
        );

        this.checkAndWriteToReport(response.statusCode(),
                "Expert Profile Created");

        responseLogger.writeResponseAsLog("Expert profile");
    }

    /**
     * @param json
     */
    @Then("update information on expert profile as $json")
    public void updateProfile(@Named("json") String json) {

        this.info("Update Expert Profile");
        expertProfile.updateExpertProfile(json, expertProfile.getExpertID());


        this.checkAndWriteToReport(response.statusCode(),
                "Update Expert Profile");
    }

    /**
     *
     */
    @Then("get profile")
    public void getProfile() {

        this.info("GET Expert Profile");
        expertProfile.getProfileOfExpert(expertProfile.getExpertID());

        this.checkAndWriteToReport(response.statusCode(),
                "Expert profile loaded");
    }

    /**
     * @param id
     */
    @Then("get profile with id $id")
    public void getProfileWithID(@Named("id") String id) {

        this.info("GET Expert Profile");
        expertProfile.getProfileOfExpert(id);

        this.checkAndWriteToReport(response.statusCode(),
                "Get Profile successfully");
        responseLogger.writeResponseAsLog("Get Expert Profile");
    }

    @Then("upload media as $mediaPath")
    public void addMedia(@Named("mediaPath") String mediaPath) {

        expertProfile.uploadMedia(mediaPath);
        if (!expertProfile.getResponseOfMediaUpload().contains("errors")) {
            this.checkAndWriteToReport(HTTPCode.HTTP_OK,
                    "Media Uploaded");
        }

        responseLogger.writeResponseAsLog("Upload Media");
    }

    /**
     * Deleting an Expert profile
     **/
    @Then("delete the profile")
    public void deleteProfile() {

        this.info("Deleting Expert Profile");

        boolean isDelete = expertProfile.deleteProfile(expertProfile.getExpertID());
        this.AssertAndWriteToReport(isDelete,
                "Expert profile deleted");

    }

    /**
     * @param id
     */
    @Then("delete the profile with id $id")
    public void deleteProfile(@Named("id") String id) {

        this.info("Delete Expert Profile");
        boolean isDelete = expertProfile.deleteProfile(id);
        this.AssertAndWriteToReport(isDelete,
                "Profile deleted with id->" + id);

    }

    /**
     * Test cases to drive the Calling API
     */
    /*Calling API Test cases*/
    @Then("generate a call with $json")
    public void generateCall(@Named("json") String json) {

        this.info("Initiating a call");
        call.doCall(json);

        this.checkAndWriteToReport(response.statusCode(),
                "Call Intialted");
        responseLogger.writeResponseAsLog("Generate Call");

    }

    @Then("accept the call")
    public void acceptCall() {

        this.info("accepting Call");
        call.isAcceptCall();

        this.checkAndWriteToReport(response.statusCode(),
                "Call Accepted");
        responseLogger.writeResponseAsLog("Accept Call");
    }

    @Then("decline the call")
    public void declineCall() {

        this.info("Declining a Call");
        call.isDecline();
        this.checkAndWriteToReport(response.statusCode(),
                "Call Declined");
        responseLogger.writeResponseAsLog("Decline Call");
    }

    @Then("delay the call")
    public void delayCall() {

        this.info("Delaying the call");
        call.isDelay();
        this.checkAndWriteToReport(response.statusCode(),
                "Call delayed");
        responseLogger.writeResponseAsLog("Delay Call");
    }

    @Then("disconnect the call")
    public void disconnectCall() {

        this.info("Disconnecting a call");
        call.isDissconnectCall();
        this.checkAndWriteToReport(response.statusCode(),
                "Call Disconnected");
        responseLogger.writeResponseAsLog("Disconnect Call");
    }

    /* Phone code verification test cases*/

    @When("we provide phone number as $phone")
    public void sendCode(@Named("phone") String phoneNo) {

        this.info("Sending phone code");
        phone.phoneCodeSend(phoneNo);

        this.checkAndWriteToReport(response.statusCode(),
                "Phone code sent");
        responseLogger.writeResponseAsLog("Phone code sent");

        /*Phone code resent*/

        this.info("Resending phone code");
        phone.phoneCodeResend(phoneNo);

        this.checkAndWriteToReport(response.statusCode(),
                "Phone code resent");
        responseLogger.writeResponseAsLog("Phone code resent");
    }

    @Then("verification code should be sent")
    public void verifyCode() {

        this.info("phone code ");
        this.AssertAndWriteToReport(phone.isCodeSent(),
                "Code sent is-->" + phone.getCode());
    }

    @Then("phone should be verified")
    public void verifyPhone() {

        this.info("Mobile No verify");
        phone.verfiyPhone(phone.getCode());

        this.checkAndWriteToReport(response.statusCode(),
                "Mobile no verified");
        responseLogger.writeResponseAsLog("Mobile no verify");
    }

    /*Social Links Test Cases*/

    @When("post social links from $social")
    @Then("post social links from $social")
    @Pending
    public void postSocialLink(@Named("social") String social) {

        this.info("Posting Social Links..");
        socialLinks.postSocialLink(social);

        this.checkAndWriteToReport(response.statusCode(),
                "Social link posted");
        responseLogger.writeResponseAsLog("Post Social Link");
    }

    @Then("get the social links")
    public void getSocialLinks() {

        this.info("Getting Social links ");
        socialLinks.getSocialLinks();

        this.checkAndWriteToReport(response.statusCode(),
                "Social link get Successful");
        responseLogger.writeResponseAsLog("Get Social Link");
    }

    /**
     * Adding social link to expert profile
     */

    @Then("add social link to expert profile")
    @When("add social link to expert profile")
    public void addSocialLinkToExpertProfile() {

        this.info("Adding social link to expert profile");

        socialLinks.addLinksToExpertProfile(expertProfile.getExpertID());

        this.checkAndWriteToReport(response.statusCode(),
                "Social link added to expert profile->" + expertProfile.getExpertID());

        responseLogger.writeResponseAsLog("Add social link to expert profile");
    }

    /**
     *
     */
    @Then("list all social links of a ExpertProfile")
    @When("list all social links of a ExpertProfile")
    public void listAllSocialLinks() {

        this.info("Getting social links linked to expert profile");

        socialLinks.getLinkedListsOfExpertProfile(expertProfile.getExpertID());

        this.checkAndWriteToReport(response.statusCode(),
                "Successfully get all links->" + expertProfile.getExpertID());

        responseLogger.writeResponseAsLog("Get social link from expert profile");
    }


    @Then("add $url as RSS Feed")
    @When("add $media as RSS Feed")
    public void addFeed(@Named("url") String url) {

        this.info("Adding RSS feed ");
        socialLinks.addFeedsToSocialLink(url);

        this.checkAndWriteToReport(response.statusCode(),
                "Successfully added RSS feed->" + expertProfile.getExpertID());
        responseLogger.writeResponseAsLog("Add RSS feed");
    }


    @Then("remove one social link")
    @When("remove one social link")
    public void removeSocialLink() {

        this.info("Removing social link");
        boolean isDelete = socialLinks.deleteSocialLink(socialLinks.getSocialLinkId().get(0));
        this.AssertAndWriteToReport(isDelete,
                "Successfully removed social links");
    }

    @Then("count of social links should be $count")
    public void assertCount(@Named("count")String count){

        this.info("Asserting social link counts");
        String countOfSocialLinks=socialLinks.getSocialLinkCount();
        if(count.equals(countOfSocialLinks)){

            this.AssertAndWriteToReport(true, "count matched");
        }else {
            this.AssertAndWriteToReport(false, "count does not matched");
        }
    }

    @When("add $socialMedia to social Link")
    @Then("add $socialMedia to social Link")
    public void addSocialMedia(@Named("SocialMedia")String SocialMedia ){


    }
}


