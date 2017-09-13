package expertchat.bdd;

// Created by Kishor on 1/25/2017.

import expertchat.report.Report;
import com.relevantcodes.extentreports.ExtentReports;

public abstract class AbstractSteps extends Report {

    protected AbstractSteps(ExtentReports reports, String casName) {

        super(reports, casName);
    }

}
