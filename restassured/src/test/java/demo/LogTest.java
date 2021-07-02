package demo;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class LogTest {
    /**
    * @Description: 打印部分日志
    * @Author: JessieXu
    * @Date: 2021/6/3
    */
    @Test
    public void testLogOnly() {
        given().
                get("http://jsonplaceholder.typicode.com/photos/").
                then().
                log().status().
                log().headers();
    }

    /**
     * @Description: 错误时才打印日志
     * @Author: JessieXu
     * @Date: 2021/6/3
     */
    @Test
    public void testLogOnlyError() {
        given().
                get("http://jsonplaceholder.typicode.com/phot/").
                then().
                log().ifError();
    }
    /**
     * @Description: 在特定条件下才打印日志
     * @Author: JessieXu
     * @Date: 2021/6/3
     */
    @Test
    public void testLogUnderConditional() {
        given().
                get("http://jsonplaceholder.typicode.com/photos/").
                then().body("title",hasItem("in debitis qui aut")).
//                log().ifStatusCodeIsEqualTo(200).
                log().ifValidationFails();
    }
}
