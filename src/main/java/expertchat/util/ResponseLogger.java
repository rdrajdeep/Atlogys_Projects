package expertchat.util;

import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.bussinesslogic.ExpertProfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class ResponseLogger {

    /*Instance member*/
    private FileWriter writer = null;
    private PrintWriter printWriter = null;
    private BufferedWriter bufferedWriter = null;
    private ParseResponse response;
    private File file;

    public ResponseLogger(ParseResponse response) {

        file=new File("Log/" + ExpertChatUtility.directoryName() + "/ResponseLog.json");
        file.getParentFile().mkdirs();
        this.response = response;
    }


    public void writeResponseAsLog(String apiName) {

        try {
            writer = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(writer);
            printWriter = new PrintWriter(bufferedWriter);

            if(apiName.contains("media")){
                printWriter.print("API RESPONSES+" +
                        "\n========\n"+
                        apiName+
                        "\n========"+"\n"+
                        new ExpertProfile().getResponseOfMediaUpload());

                printWriter.flush();
            }

            printWriter.print("API RESPONSES+" +
                    "\n========\n"+
                    response.writeResponseToLog(apiName)[0]+
                    "\n========"+"\n"+
                    response.writeResponseToLog(apiName)[1]+"\n");

            printWriter.flush();

        } catch (IOException e) {

            throw new ExpertChatException(e.getMessage());

        } finally {
            try {
                    writer.close();
                    printWriter.close();
                    bufferedWriter.close();
            } catch (IOException e) {

                throw new ExpertChatException(e.getMessage());
            }

        }
    }

}
