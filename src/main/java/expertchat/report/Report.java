package expertchat.report;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import static expertchat.apioperation.apiresponse.HTTPCode.*;

import expertchat.apioperation.apiresponse.HTTPCode;
import expertchat.apioperation.apiresponse.ParseResponse;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.steps.Steps;


public class Report extends Steps {

    protected   ExtentReports reports;
    protected   ExtentTest test;

    public Report(ExtentReports reports , String casName){
        this.reports=reports;
        test=this.reports.startTest(casName);

    }

    public void pass(String description, String apiResponse){

        test.log(LogStatus.PASS, description, apiResponse);
    }

    public void pass(String description){

        test.log(LogStatus.PASS, description);
        reports.flush();
    }


    public void fail(String description, String apiResponse){

        test.log(LogStatus.FAIL, description, apiResponse);
    }

    public void fail(String description){

        test.log(LogStatus.FAIL, description);
        reports.flush();
    }


    public void warning(String warning){

        test.log(LogStatus.WARNING, warning);
        reports.flush();

    }

    public void info(String information) {

        test.log(LogStatus.INFO,information);
        reports.flush();
    }

    /**
     *
     * @param statusCode
     * @param successMessage
     */
    public void checkAndWriteToReport(int statusCode, String successMessage){

        if(statusCode== HTTPCode.HTTP_OK || statusCode==HTTPCode.HTTP_ACCEPTED){

            pass(successMessage);
        }else {

            fail("Something went wrong. Please check");
        }
    }

    public void checkErrorCode(int serverCode, String actual, String expected) {

        if (serverCode == HTTP_BAD && expected.equals(actual)) {

            pass("Code matched" + actual);

        } else {
            warning("Looks like you have not provided a proper code in test case");
        }
    }

    public void checkSuccessCode(int serverCode, String actual, String expected){

        if (serverCode == HTTP_OK && expected.equals(actual)) {

            pass("Code matched"+actual);

        } else {
            warning("Looks like you have not provided a proper code in test case");
        }
    }

    public void checkNonFiledError(int serverCode, String actual, String expected){

        if (serverCode == HTTP_BAD && expected.equals(actual)) {

            pass("Code matched"+actual);

        } else {
            warning("Looks like you have not provided a proper code in test case");
        }
    }

    public void AssertAndWriteToReport(boolean bool, String successMessage){

        if(bool){

            pass(successMessage);
        }else {

            fail("something went wrong");
        }
    }

    @AfterStory
    public void end(){

        reports.flush();
        reports.endTest(test);

    }

}
