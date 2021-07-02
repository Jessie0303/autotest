package demo;

import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ResponseAssertTest {
    /**
    * @Description: 响应正文中的属性使用匿名类来断言
    * @Author: JessieXu
    * @Date: 2021/6/3
    */
    @Test
    public void anonymousClassAssert(){
        given().
                get("http://jsonplaceholder.typicode.com/photos/1/").
                then().
                body("thumbnailUrl", new ResponseAwareMatcher<Response>() {
                    @Override
                    public Matcher<?> matcher(Response response) throws Exception {
                        return equalTo("https://via.placeholder.com/150/92c952");
                    }
                });
    }

    /**
     * @Description: 响应正文中的属性使用lambda表达式来断言
     * @Author: JessieXu
     * @Date: 2021/6/3
     */
    @Test
    public void lambdaAssert(){
        given().
                get("http://jsonplaceholder.typicode.com/photos/1/").
                then().
                body("thumbnailUrl",response -> equalTo("https://via.placeholder.com/150/92c952")).
                body("title",endsWith("sunt"));
    }

    /**
    * @Description:响应时间time,这个包含，http请求加响应处理时间 加上我们使用rest assured这个工具产生的时间之和
    * @Author: JessieXu
    * @Date: 2021/6/3
    */
    @Test
    public void getResponseTime(){
        long t1 = given().get("http://jsonplaceholder.typicode.com/photos/").getTime();
        long t2 = given().get("http://jsonplaceholder.typicode.com/photos/").getTimeIn(TimeUnit.SECONDS);
        long t3 = given().get("http://jsonplaceholder.typicode.com/photos/").time();
        long t4 = given().get("http://jsonplaceholder.typicode.com/photos/").timeIn(TimeUnit.SECONDS);
        given().get("http://jsonplaceholder.typicode.com/photos/").then().time(lessThan(2000L));
        System.out.println("getTime(ms):"+t1);
        System.out.println("getTime(s):"+t2);
        System.out.println("time(ms):"+t3);
        System.out.println("time(s):"+t4);
    }
}
