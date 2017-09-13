package expertchat.apioperation.session;

// Created by Kishor on 1/23/2017.


public final class SessionManagement {

    private static SessionManagement sm=new SessionManagement();

    private SessionManagement(){

    }

    public static SessionManagement session(){

        return sm;
    }

    private String token;

    public void setToken(String token){

        this.token=token;
    }

    public String getToken(){

        return token;
    }

}
