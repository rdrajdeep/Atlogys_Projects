package expertchat.apioperation;

// Created by Kishor on 1/24/2017.

import io.restassured.response.Response;

public interface ApiFactories {


    Response post(String json, String url);

    Response post(String json, String url, String token);

    Response post(String json, String url, String token, boolean port);

    Response get(String url);

    Response get(String url, String token);

    Response put(String json, String url, String token);

    Response put(String json, String url, String token, boolean port);

    Response delete(String json, String url, String token);

    Response delete(String json, String url, String token, boolean port);

    boolean isDelete(String url, String token);

    Response patch(String json, String url, String token);


}
