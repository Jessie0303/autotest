package demo;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ResponseBodyTest {

    /**
     * @Description: 响应直接保存
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    public void getResponseThenDoActions() throws IOException {
        Response res =
                given().body("{\"mobilephone\":\"13323234545\",\"password\":\"123456\"}").
                        when().post("http://httpbin.org/post").
                        then().extract().response();
        System.out.println(res.statusCode());
        System.out.println(res.headers());
        System.out.println(res.contentType());
        System.out.println((String) res.path("url"));
    }

    /**
    * @Description: 响应转为String
    * @Author: JessieXu
    * @Date: 2021/6/2
    */
    @Test
    void getResAsString(){
        String response = given().body("{\"mobilephone\":\"13323234545\",\"password\":\"123456\"}")
                .when().
                post("http://httpbin.org/post").asString();
        System.out.println(response);
    }

    /**
     * @Description: 响应转为输入流
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    void getResAsInputStream() throws IOException {
        InputStream response = given().body("{\"mobilephone\":\"13323234545\",\"password\":\"123456\"}")
                .when().
                        post("http://httpbin.org/post").asInputStream();
        System.out.println(response.toString());
        response.close();
    }

    /**
     * @Description: 响应转换成字节数组
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    public void getResponseAsByteArray() throws IOException {
        byte[] bytes = given().get("https://reqres.in/api/users/2").asByteArray();
        System.out.println("The Response: \n\n" + bytes.length);
        //遍历这个字节数组看看
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
    }

    /**
     * @Description: 用xpath方式获取body
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    void dealResWithXpath(){
        given().
                body("{\"mobilephone\":\"13323234545\",\"password\":\"123456\"}").
                when().
                post("http://httpbin.org/post").
                then().
                statusCode(200).
                body("headers.Accept-Encoding",equalTo("gzip,deflate")).
                body("json.mobilephone",equalTo("13323234545"));
    }

    /**
     * @Description: 用root存储xpath的父路径，body只需要获取子节点
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    void dealResWithRoot(){
        given().
                body("{\"mobilephone\":\"13323234545\",\"password\":\"123456\"}").
                when().
                post("http://httpbin.org/post").
                then().
                statusCode(200).
                rootPath("json").
                body("mobilephone",equalTo("13323234545"));

    }



    /**
     * @Description: 使用path方法提取内容
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    void dealResWithPath(){
        String pwd =
        given().
                body("{\"mobilephone\":\"13323234545\",\"password\":\"123456\"}").
                when().
                post("http://httpbin.org/post").path("json.password");
        System.out.println(pwd);
    }

    /**
    * @Description:Bean类序列化成JSON对象
    * @Author: JessieXu
    * @Date: 2021/6/3
    */
    @Test
    void testSerializationUsingBeanClass(){
        Message msg = given().get("http://jsonplaceholder.typicode.com/photos/1/").as(Message.class);
        System.out.println(msg.getThumbnailUrl());
    }
}
