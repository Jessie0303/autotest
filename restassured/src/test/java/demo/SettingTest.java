package demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.SessionConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class SettingTest {
    @BeforeAll
    static void init(){
        RestAssured.baseURI="http://httpbin.org";
        RestAssured.port = 80;
        RestAssured.basePath = "/get";
        //基本认证（登录认证）
        RestAssured.authentication = basic("username","password");
        //过滤器
        RestAssured.filters();
        RestAssured.urlEncodingEnabled = true;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config=RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.config=RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("phpsessionid"));
    }

    @Test
    public void testUrl(){
        given().get("?userId=1&userName=Tom").then().log().all();
    }

}
