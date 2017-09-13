package expertchat.util;

// Created by Kishor on 1/24/2017.

public class Email {


    public void sendEmail(){

        if(ExpertChatException.EXIT){

            emailEventError(ExpertChatException.message);
        }
    }

    private void emailEventError(String EmailMessage) {

        System.out.println("Email Send");
    }
}
