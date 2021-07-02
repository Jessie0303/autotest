package wework.api.contact;

import io.restassured.response.Response;
import wework.filter.TokenFilter;
import wework.utils.FakerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TagApi {
    static String suffix = FakerUtils.getTimeStamp();

    /**
    * @Description: 创建标签
    * @Author: JessieXu
    * @Date: 2021/6/17
    */
    public static Response createTag(TokenFilter tokenFilter,String tag){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "   \"tagname\": \""+tag+suffix+"\"\n" +
                        "}").
                post("https://qyapi.weixin.qq.com/cgi-bin/tag/create").
                then().log().all().extract().response();
        return res;
    }

    /**
    * @Description: 更新标签名字
    * @Author: JessieXu
    * @Date: 2021/6/17
    */
    public static Response updateTag(TokenFilter tokenFilter,int tagId,String tagName){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "   \"tagid\": "+tagId+",\n" +
                        "   \"tagname\": \""+tagName+suffix+"\"\n" +
                        "}").
                post("https://qyapi.weixin.qq.com/cgi-bin/tag/update").
                then().log().all().extract().response();
        return res;
    }

    /**
    * @Description: 删除标签
    * @Author: JessieXu
    * @Date: 2021/6/17
    */
    public static Response deleteTag(TokenFilter tokenFilter,int tagId){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                queryParam("tagid",tagId).
                get("https://qyapi.weixin.qq.com/cgi-bin/tag/delete?debug=1").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:获取标签成员
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    public static Response getTagMember(TokenFilter tokenFilter,int tagId){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                queryParam("tagid",tagId).
                get("https://qyapi.weixin.qq.com/cgi-bin/tag/get").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:增加标签成员
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    public static Response addTagMember(TokenFilter tokenFilter, int tagId,ArrayList<String> userlist,ArrayList<Long> partylist){
        Map<String,Object> params = new HashMap();
        params.put("tagid",tagId);
        params.put("userlist",userlist);
        params.put("partylist",partylist);
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                body(params).
                post("https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers?debug=1").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:删除标签成员
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    public static Response delTagMember(TokenFilter tokenFilter, int tagId,ArrayList<String> userlist,ArrayList<Long> partylist){
        Map<String,Object> params = new HashMap();
        params.put("tagid",tagId);
        params.put("userlist",userlist);
        params.put("partylist",partylist);
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                body(params).
                post("https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers?debug=1").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:获取标签列表
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    public static Response getTagList(TokenFilter tokenFilter){
        Response res = given().filter(tokenFilter).log().all().
                contentType("application/json; charset=UTF-8").
                get("https://qyapi.weixin.qq.com/cgi-bin/tag/list").
                then().log().all().extract().response();
        return res;
    }
}
