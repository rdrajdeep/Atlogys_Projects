package expertchat.apioperation;

public interface ExpertChatEndPoints {

    String REGISTER="register/";

    String LOGIN="login/";

    String CHANGE_PASSWORD="change-password/";

    String RESEND_EMAIL_VERIFICATION="resend/";

    String BASIC_PROFILE="me-basic-info/";

    String ME_PHOTO="me-photo/";

    /*Social Link URLS*/

    String SOCIAL="http://api.qa.experchat.com/v1/";

    String SOCIAL_LINKS="social-links/";

    String PROFILE=SOCIAL+"expert"+"/"+"profile/";

    String GET_FEEDS=SOCIAL+"social/get_feeds/";

    String FEED_LINK=SOCIAL+"feed/addlink/";

    String EXPERT_PROFILE="expertprofiles/";

    String PUBLISH_CONTENT=SOCIAL+"contents/";


    String SESSION="sessions/";

    String PHONECODEVERIFY="phonecodeverify/";

    String PHONECODERESEND="resendphonecode/";

    String PHONECODESEND="phonecodesend/";

}
