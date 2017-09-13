package expertchat.apioperation.httpspecs;

// Created by Kishor on 1/23/2017.

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static expertchat.util.ExpertChatUtility.getValue;

public class Specification {


    public static RequestSpecBuilder builder;
    private static RequestSpecification requestSpec;

    public static RequestSpecification setupRequestSpecBuilder(){

        builder=new RequestSpecBuilder();
        builder.setBaseUri(getValue("qa"));
        builder.setBasePath(getValue("basepath"));
        builder.setContentType(ContentType.JSON);
        requestSpec=builder.build();
        return requestSpec;

    }

    public static RequestSpecification setupRequestSpecBuilderWithPort(){

        builder=new RequestSpecBuilder();
        builder.setBaseUri(getValue("qawithport"));
        builder.setContentType(ContentType.JSON);
        requestSpec=builder.build();
        return requestSpec;

    }

}
