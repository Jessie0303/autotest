package demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sun.jvm.hotspot.memory.HeapBlock;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.containsString;

public class ResponseHeaderTest {

    @BeforeAll
    public static void init(){
        useRelaxedHTTPSValidation();
        RestAssured.proxy("127.0.0.1",8888);
    }
    /**
    * @Description: 获取指定header和遍历headers
    * @Author: JessieXu
    * @Date: 2021/6/2
    */
    @Test
    void getHeaders(){
        Response res = given().get("http://jsonplaceholder.typicode.com/photos").then().extract().response();
        System.out.println("Single output Content-Type :"+res.getHeader("Content-Type"));
        Headers heads = res.getHeaders();
        for(Header h:heads){
            System.out.println(h.getName()+":"+h.getValue());
        }
    }

    /**
    * @Description:获取cookies
    * @Author: JessieXu
    * @Date: 2021/6/2
    */
    @Test
    void getCookies(){
        String cookies = "wr_localvid=; wr_name=; wr_avatar=; wr_gender=; wr_gid=211972311; wr_vid=10117666; wr_skey=RXFoQuHR; wr_pf=0; wr_rt=web%40yOrCqMr0z1XiMzNTdgR_WL";
        String[] cookiesArr = cookies.split(";");
        Map<String,String> cookieMap = new HashMap<>();
        for(String cookie:cookiesArr){
            String key = cookie.split("=")[0];
            String value = "";
            if(cookie.indexOf("=")!=cookie.length()-1){
                value = cookie.split("=")[1];
            }
//            System.out.println("key:"+key+",value:"+value);
            cookieMap.put(key,value);
        }

        Response res = given().cookies(cookieMap).
                get("https://weread.qq.com/").then().extract().response();
        Map<String,String> resCookies = res.getCookies();

        for(Map.Entry<String,String> cookie:resCookies.entrySet()){
            System.out.println(cookie.getKey()+":"+cookie.getValue());
        }
        given().cookies(cookieMap).
                get("https://weread.qq.com/").then().log().all();
    }

    @Test
    public void assertWithStatus(){
        given().get("http://httpbin.org/get").then().assertThat().
                statusCode(200).
                statusLine("HTTP/1.1 200 OK").
                statusLine(containsString("OK"));
    }

    @Test
    public void assertWithHeader(){
        given().get("http://httpbin.org/get").then().assertThat().
                header("Content-Type", "application/json").
                header("Connection","keep-alive").
                headers("Server","gunicorn/19.9.0","Access-Control-Allow-Credentials","true");
    }

    /**
     * @Description:校验响应内容格式是html
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    public void testResponseContentType2() {
        given().
                get("https://www.baidu.com").
                then().
                statusCode(200).
                contentType(ContentType.HTML);
    }

    /**
     * @Description:校验响应内容格式是json
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    public void testResponseContentType() {
        given().
                get("http://jsonplaceholder.typicode.com/photos/1").
                then().
                statusCode(200).
                contentType(ContentType.JSON);
    }

    /**
     * @Description:校验响应内容格式是xml
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    public void testResponseContentType3() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/INVOICE/").
                then().
                statusCode(200).
                contentType(ContentType.XML);
    }

}
