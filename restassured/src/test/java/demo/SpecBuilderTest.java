package demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.poi.poifs.crypt.agile.AgileEncryptionVerifier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class SpecBuilderTest {
    static RequestSpecification requestSpc;
    static ResponseSpecification responseSpc;
    @BeforeAll
    public static void reqAndResBuilder(){
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.addHeader("Name","Tom");
        reqBuilder.addHeader("Age","35");
        Map<String,String> map = new HashMap<>();
        map.put("key1","val1");
        map.put("key2","val2");
        map.put("key3","val3");
        reqBuilder.addParams(map);
        reqBuilder.addFormParam("keyA","valA","valB");
        requestSpc = reqBuilder.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectBody("args.key1",equalTo("val1"));
        resBuilder.expectContentType(ContentType.JSON);
        resBuilder.expectStatusCode(200);
        resBuilder.expectBody("args.keyA",hasItem("valA"));
        responseSpc = resBuilder.build();
    }

    @Test
    public void getResponse(){
        given().spec(requestSpc).log().all().
                get("http://httpbin.org/get").
                then().
                spec(responseSpc);
    }
}
