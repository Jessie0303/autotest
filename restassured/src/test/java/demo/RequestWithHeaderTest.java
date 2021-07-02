package demo;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestWithHeaderTest {

    /**
     * @Description: 请求头
     * @Author: JessieXu
     * @Date: 2021/6/2
     */
    @Test
    public void getWithHeaders(){
        given().headers("key1", "val1", "key2", "val2", "key3", "val3").
                header("key4", "val4").
                header("key5", "val5", "val6").
                get("http://httpbin.org/get").
                then().
                statusCode(200).
                log().all();
    }

    @Test
    public void postWithHeaderAndParams(){
        given().headers("accept-encoding","gzip","accept-language","zh-CN").and().
                params("userId","2","id","14").
                post("http://httpbin.org/post").
                then().
                statusCode(200).
                log().all();
    }

    @Test
    public void postWithHeaderAndParamsMap(){
        HashMap<String,String> headers = new HashMap<>();
        headers.put("accept-encoding","gzip");
        headers.put("accept-language","zh-CN");
        HashMap<String,String> params = new HashMap<>();
        params.put("userId","2");
        params.put("id","14");
        given().headers(headers).
                params(params).
                post("http://httpbin.org/post").
                then().
                statusCode(200).
                log().all();
    }

    @Test
    void getPathParams(){
        given().
                pathParam("name","Tom").
                pathParam("id","1").
                get("http://httpbin.org/get?id={id}&name={name}").
                then().
                statusCode(200).
                log().all();
    }

    @Test
    void postPathParams(){
        given().
                pathParam("name","Tom").
                pathParam("id","1").
                post("http://httpbin.org/post?id={id}&name={name}").
                then().
                statusCode(200).
                log().all();
    }

//    @Test
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

        given().cookies(cookieMap).
                get("https://weread.qq.com/").
                then().log().all();

    }

    @Test
    void getCookies2(){
        Cookie cookie1 = new Cookie.Builder("wr_localvid","").build();
        Cookie cookie2 = new Cookie.Builder("wr_name","").build();
        Cookie cookie3 = new Cookie.Builder("wr_avatar","").build();
        Cookie cookie4 = new Cookie.Builder("wr_gender","").build();
        Cookie cookie5 = new Cookie.Builder("wr_gid","211972311").build();
        Cookie cookie6 = new Cookie.Builder("wr_vid","10117666").build();
        Cookie cookie7 = new Cookie.Builder("wr_skey","RXFoQuHR").build();
        Cookie cookie8 = new Cookie.Builder("wr_pf","0").build();
        Cookie cookie9 = new Cookie.Builder("wr_rt","web%40yOrCqMr0z1XiMzNTdgR_WL").build();

        Cookies cookies = new Cookies(cookie1,cookie2,cookie3,cookie4,cookie5,cookie6,cookie7,cookie8,cookie9);
        given().cookies(cookies).
                get("https://weread.qq.com/").
                then().statusCode(200).log().all();
    }
}
