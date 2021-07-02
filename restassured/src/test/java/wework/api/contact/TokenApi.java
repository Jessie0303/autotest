package wework.api.contact;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class TokenApi {

    /**
    * @Description:通讯录同步接口的token
     * 每个应用有独立的secret，获取到的access_token只能本应用使用，所以每个应用的access_token应该分开来获取
    * @Author: JessieXu
    * @Date: 2021/6/7
    */
    public static String getContactToken(){
        useRelaxedHTTPSValidation();
        String access_token = given().
                params("corpid","ww07fc537b079b3347","corpsecret","H1exkqc0SMtcbIjH2TyIzb57OAwbJb7CcRCGm2ONtZI")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract().response().path("access_token");
        return access_token;
    }
}
