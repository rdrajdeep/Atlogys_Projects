package expertchat.bussinesslogic;

/**
 * Created by kalpha on 1/28/2017.
 */
public class EmailVerification {

    private static String verificationEndPoint;


    public static String getVerificationEndPoint() {
        return verificationEndPoint;
    }

    public static void setVerificationEndPoint(String verificationCode) {

        verificationEndPoint = "http://api.qa.experchat.com/v1/verify/"+verificationCode+"/";

    }
}
