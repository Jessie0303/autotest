package wework.api.contact;

import io.restassured.response.Response;
import wework.filter.TokenFilter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SynBatchApi {

    /**
    * @Description:上传临时素材
     * 通过上传媒体文件接口上传数据文件，获取media_id
    * @Author: JessieXu
    * @Date: 2021/6/18
    */
    public static Response uploadTemp(TokenFilter tokenFilter,String filename){
        Response res = given().filter(tokenFilter).log().all().
                queryParam("type","file").
                multiPart(new File(filename)).
                post("https://qyapi.weixin.qq.com/cgi-bin/media/upload?debug=1").
                then().log().all().extract().response();
        return res;
    }

    /**
    * @Description:增量更新成员
    * @Author: JessieXu
    * @Date: 2021/6/18
    */
    public static Response increaseSynUser(TokenFilter tokenFilter,String media_id,boolean to_invite){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "    \"media_id\":\""+media_id+"\",\n" +
                        "    \"to_invite\": "+to_invite+"\n" +
                        "}").
                post("https://qyapi.weixin.qq.com/cgi-bin/batch/syncuser").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:全量覆盖成员
     * @Author: JessieXu
     * @Date: 2021/6/18
     */
    public static Response coverSynUser(TokenFilter tokenFilter,String media_id,boolean to_invite){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "    \"media_id\":\""+media_id+"\",\n" +
                        "    \"to_invite\": "+to_invite+"\n" +
                        "}").
                post("https://qyapi.weixin.qq.com/cgi-bin/batch/replaceuser").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:全量覆盖部门
     * @Author: JessieXu
     * @Date: 2021/6/18
     */
    public static Response coverDepartment(TokenFilter tokenFilter,String media_id){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "    \"media_id\":\""+media_id+"\"\n" +
                        "}").
                post("https://qyapi.weixin.qq.com/cgi-bin/batch/replaceparty?debug=1").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:获取异步任务结果
     * @Author: JessieXu
     * @Date: 2021/6/18
     */
    public static Response getResult(TokenFilter tokenFilter,String jobId){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                queryParam("jobid",jobId).
                get("https://qyapi.weixin.qq.com/cgi-bin/batch/getresult").
                then().log().all().extract().response();
        return res;
    }
}
