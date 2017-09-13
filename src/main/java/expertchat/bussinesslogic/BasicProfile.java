package expertchat.bussinesslogic;// Created by Kishor on 2/9/2017.

import expertchat.apioperation.AbstractApiFactory;
import expertchat.apioperation.apiresponse.ApiResponse;
import expertchat.apioperation.apiresponse.ParseResponse;
import expertchat.apioperation.apiresponse.ResponseDataType;
import expertchat.apioperation.session.SessionManagement;
import expertchat.usermap.TestUserMap;
import org.apache.commons.io.IOUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import static expertchat.apioperation.ExpertChatEndPoints.BASIC_PROFILE;
import static expertchat.apioperation.ExpertChatEndPoints.ME_PHOTO;

public class BasicProfile extends AbstractApiFactory {

    private ApiResponse response=ApiResponse.getObject();

    private ParseResponse parseResponse=new ParseResponse(response);


    public void loadBasicProfile(){

        response.setResponse(
                this.get(BASIC_PROFILE, SessionManagement.session().getToken())
        );

        response.printResponse();
    }

    public boolean verifyProfileEmail(String user){

        String actualEmail=parseResponse.getJsonData("results.email", ResponseDataType.STRING);

        String expectedEmail= TestUserMap.getEmailOf(user);
        if(actualEmail.equals(expectedEmail)){

            return true;
        }
        return false;
    }


    public void addName(String name){

        response.setResponse(
                this.put(

                        name, BASIC_PROFILE, SessionManagement.session().getToken()
                )
        );

        response.printResponse();
    }

    public void addProfilePhoto(String image) throws IOException{

        byte[] imageInByte;
        BufferedImage originalImage = ImageIO.read(new File(image));

        // convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();
        String b64String="\""+"data:image/png;base64,"+Base64.getEncoder().encodeToString(imageInByte)+"\"";
        String json="{\"image\":"+b64String+"}";
        response.setResponse(
                this.post(
                        json, ME_PHOTO, SessionManagement.session().getToken()
                )
        );

        response.printResponse();

    }
}
