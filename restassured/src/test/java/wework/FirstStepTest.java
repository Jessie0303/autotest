package wework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class FirstStepTest {
    static String access_token;
    //获取Access token
    @BeforeAll
    public static void getToken(){
        access_token = given().params("corpid","ww07fc537b079b3347","corpsecret","BeyX2-pdDIFgtd95bb9k9QlN61QFI1RvakhiKfj70Go")
                .get(" https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract().path("access_token");
        System.out.println(access_token);
    }

    //获取企业微信API域名IP段
    @Test
    void getIps(){
        given()
                .params("access_token",access_token)
                .get("https://qyapi.weixin.qq.com/cgi-bin/get_api_domain_ip")
                .then().statusCode(200)
                .log().all();
    }

}
