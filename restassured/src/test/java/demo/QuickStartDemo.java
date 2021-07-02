package demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.*;

public class QuickStartDemo {

    static String access_token;
    //获取access_token
    @BeforeAll
    public static void getAccessToken(){
        useRelaxedHTTPSValidation();
        RestAssured.proxy("127.0.0.1",8888);
        access_token = given()
                .params("corpid","ww07fc537b079b3347","corpsecret","BeyX2-pdDIFgtd95bb9k9QlN61QFI1RvakhiKfj70Go")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract().response().path("access_token");
        System.out.println(access_token);
    }



    //在请求中携带 Cookies 信息
    @Test
    void postMethod2(){
        given().
                cookie("cookieName","cookieValue").
                when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }

    //指定多对 cookie
    @Test
    void postMethod3(){
        given().cookies("cookieName1","cookieValue1","cookieName2","cookieValue2")
                .when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }


    @Test
    void postMethod(){

        given()
                .contentType("application/json;charset=utf-8")
                .body("{" +
                        "   \"touser\" : \"@all\"," +
                        "   \"msgtype\" : \"text\"," +
                        "   \"agentid\" : 1000003," +
                        "   \"text\" : {" +
                        "       \"content\" : \"你的快递已到，请携带工卡前往邮件中心领取。\\n出发前可查看<a href=\\\"http://work.weixin.qq.com\\\">邮件中心视频实况</a>，聪明避开排队。\"" +
                        "   }" +
                        "}")
                .queryParam("access_token",access_token)
                .post("https://qyapi.weixin.qq.com/cgi-bin/message/send")
                .then()
                .log().all()
                .statusCode(200)
                .body("errcode",equalTo(0));
    }

    /**
    * @Description:Header 设置
    * @Author: JessieXu
    * @Date: 2021/6/1
    */
    @Test
    void postMethod4(){
        given().header("headerName","value")
                .when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }

    /**
    * @Description: Content Type 设置
    * @Author: JessieXu
    * @Date: 2021/6/1
    */
    @Test
    void postMethod5(){
        given().contentType("application/json")
                //或者指定下面的形式
                //.contentType(ContentType.JSON)
                .when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }

    /**
    * @Description:Request Body 设置
    * @Author: JessieXu
    * @Date: 2021/6/1
    */
    @Test
    void postMethod6(){
        given().body("{\"mobilephone\":\"13323234545\",\"password\":\"123456\"}")
                .when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }

    /**
    * @Description:将 Java 对象序列化为 JSON 或者 XML
    * @Author: JessieXu
    * @Date: 2021/6/1
    */

    //通过 contentType 指定为 JSON，将 HashMap 序列化为 JSON
    @Test
    public void testJson1(){
        HashMap<String,String> hashMap= new HashMap<String,String>();
        hashMap.put("firstName","jack");
        hashMap.put("lastName","tom");
        given().
                contentType(ContentType.JSON).
                body(hashMap).
                when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }

    //通过 contentType 指定为 JSON，将 Message 对象序列化为 JSON
    @Test
    public void testJson2(){
        Message message = new Message();
        message.setMessage("tester");
        given().
                contentType(ContentType.JSON).
                body(message).
                when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }

    //通过 contentType 指定为 XML，将 Message 对象序列化为 XML
    @Test
    public void testXml1(){
        Message message = new Message();
        message.setMessage("tester");
        given().
                contentType(ContentType.JSON).
                body(message).
                when().
                post("http://httpbin.org/post").
                then().
                log().body();
    }

    /**
    * @Description: 校验响应数据
    * @Author: JessieXu
    * @Date: 2021/6/1
    */
    @Test
    public void verify(){
        given().
                cookie("cookieName","cookieValue").
                contentType(ContentType.JSON).
                body("{{" +
                        "  \"userId\": 1," +
                        "  \"id\": 3," +
                        "  \"title\": \"value1\"," +
                        "  \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis \"" +
                        "}," +
                        "{" +
                        "  \"userId\": 1," +
                        "  \"id\": 3," +
                        "  \"title\": \"value2\"," +
                        "  \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis \"" +
                        "}," +
                        "{" +
                        "  \"userId\": 1," +
                        "  \"id\": 3," +
                        "  \"title\": \"value3\"," +
                        " \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis \"" +
                        "}" +
                        "}").
                when().
                post("http://httpbin.org/post").
                then().log().body();
//                assertThat().statusCode(200).
//                assertThat().contentType("application/json").
//                assertThat().header("Connection","keep-alive").
//                body("url",equalTo("http://httpbin.org/post"),"origin",equalTo("171.83.44.186")).
//                and().
//                body("title",hasItems("value1","value2", "value3"));
    }

    @Test
    void testEqualToMethod(){
        HashMap<String,String> hashMap= new HashMap<String,String>();
        hashMap.put("firstName","jack");
        hashMap.put("lastName","tom");
        given().body(hashMap).
                post("http://httpbin.org/post").
                then().
                body("data",equalTo("{\"firstName\":\"jack\",\"lastName\":\"tom\"}")).
                and().
                body("origin",equalTo("171.83.44.186"));
    }

}
