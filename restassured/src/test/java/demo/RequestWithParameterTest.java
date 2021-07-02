package demo;

import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class RequestWithParameterTest {
    /**
     * @Description: param 参数设置
     * @Author: JessieXu
     * @Date: 2021/6/1
     */

    @BeforeAll
    public static void init(){
        useRelaxedHTTPSValidation();
        RestAssured.proxy("127.0.0.1",8888);
        RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.config().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"));
    }

    //given 中配置 queryParam 查询参数
    @Test
    void getWithQueryParam(){
        given().queryParam("userId",2)
                .contentType("application/json;charset=utf-8")
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .log().body();
    }

    //GET方法将会自动使用查询参数
    @Test
    void getWithParam(){
        given()
                .param("corpid","ww07fc537b079b3347")
                .param("corpsecret","BeyX2-pdDIFgtd95bb9k9QlN61QFI1RvakhiKfj70Go")
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .log().all();
    }


    @Test
    public void getWithParamsMap() {
        //构造一个Map对象，用来存多个参数和值
        Map<String, String> parameters = new HashMap<String,String>();
        parameters.put("userId", "2");
        parameters.put("id", "14");

        given().
                params(parameters).
                when().
                get("http://jsonplaceholder.typicode.com/posts").
                then().
                statusCode(200).log().all();
    }

    @Test
    public void getWithParams() {
        given().params("userId","2","id","14").
                when().
                get("http://jsonplaceholder.typicode.com/posts").
                then().
                statusCode(200).log().all();
    }

    @Test
    void postWithFormParam(){
        given().contentType("application/x-www-form-urlencoded; charset=UTF-8").
                formParam("name","Tom").
                formParam("sex","男").
                post("http://httpbin.org/post").then().log().all();
    }

    @Test
    void postWithParams(){
        List<String> cources = new ArrayList<>();
        cources.add("Chinese");
        cources.add("English");
        cources.add("Math");
        Map<String,String> students = new HashMap<>();
        students.put("20210001","张三");
        students.put("20210002","李四");
        students.put("20210003","王五");
        given().config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"))).
                param("classes","201","202","203").
                param("cources",cources).
                param("students",students).
//                formParam("students",students).
                post("http://httpbin.org/post").then().log().all();
    }

}
