package wework.api.contact;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import wework.filter.TokenFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DepartmentMemberApi {


    /**
     * @Description: 创建成员(管理工具-通讯录同步)
     * @Author: JessieXu
     * @Date: 2021/6/3
     */
    public static Response createMember(String userid,String name,String ename,Long department){
        ArrayList<Object> departmentList = new ArrayList<>();
        departmentList.add(department);
        Response res = given().filter(new TokenFilter()).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "    \"userid\": \""+userid+"\",\n" +
                        "    \"name\": \""+name+"\",\n" +
                        "    \"alias\": \""+ename+"\",\n" +
                        "    \"department\": "+departmentList+", \n" +
                        "    \"order\":[10,40],\n" +
                        "    \"position\": \"产品经理\",\n" +
                        "    \"gender\": \"1\",\n" +
                        "    \"email\": \""+userid+"@gzdev.com\",\n" +
                        "    \"is_leader_in_dept\": [1],\n" +
                        "    \"enable\":1,\n" +
                        "    \"telephone\": \"020-123456\",\n" +
                        "    \"address\": \"广州市海珠区新港中路\",\n" +
                        "    \"main_department\": 1,\n" +
                        "    \"extattr\": {\n" +
                        "        \"attrs\": [\n" +
                        "            {\n" +
                        "                \"type\": 0,\n" +
                        "                \"name\": \"文本名称\",\n" +
                        "                \"text\": {\n" +
                        "                    \"value\": \"文本\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"type\": 1,\n" +
                        "                \"name\": \"网页名称\",\n" +
                        "                \"web\": {\n" +
                        "                    \"url\": \"http://www.test.com\",\n" +
                        "                    \"title\": \"标题\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"to_invite\": true,\n" +
                        "    \"external_position\": \"高级产品经理\",\n" +
                        "    \"external_profile\": {\n" +
                        "        \"external_corp_name\": \"\",\n" +
                        "        \"external_attr\": [\n" +
                        "            {\n" +
                        "                \"type\": 0,\n" +
                        "                \"name\": \"文本名称\",\n" +
                        "                \"text\": {\n" +
                        "                    \"value\": \"文本\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"type\": 1,\n" +
                        "                \"name\": \"网页名称\",\n" +
                        "                \"web\": {\n" +
                        "                    \"url\": \"http://www.test.com\",\n" +
                        "                    \"title\": \"标题\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"type\": 2,\n" +
                        "                \"name\": \"测试app\",\n" +
                        "                \"miniprogram\": {\n" +
                        "                    \"appid\": \"wx8bd8012614784fake\",\n" +
                        "                    \"pagepath\": \"/index\",\n" +
                        "                    \"title\": \"miniprogram\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}\n").log().all().
                post("https://qyapi.weixin.qq.com/cgi-bin/user/create").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description: 在通讯录同步助手中此接口可以读取企业通讯录的所有成员的信息，而自建应用可以读取该应用设置的可见范围内的成员信息。
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response getMemberInfo(String userid){
        Response res = given().filter(new TokenFilter()).log().all().
                queryParam("userid",userid).
                get("https://qyapi.weixin.qq.com/cgi-bin/user/get").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:更新成员（仅通讯录同步助手或第三方通讯录应用可调用。）
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response updateMember(String userId,String mdfname,Long department){
        ArrayList<Object> departmentList = new ArrayList<>();
        departmentList.add(department);
        Response res = given().filter(new TokenFilter()).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "    \"userid\": \""+userId+"\",\n" +
                        "    \"name\": \""+mdfname+"\",\n" +
                        "    \"department\": "+departmentList+",\n" +
                        "    \"order\": [10],\n" +
                        "    \"position\": \"后台工程师\",\n" +
                        "    \"gender\": \"1\",\n" +
                        "    \"email\": \""+userId+"@gzdev.com\",\n" +
                        "    \"is_leader_in_dept\": [1],\n" +
                        "    \"enable\": 1,\n" +
                        "    \"telephone\": \"020-123456\",\n" +
                        "    \"alias\": \"jackzhang\",\n" +
                        "    \"address\": \"广州市海珠区新港中路\",\n" +
                        "    \"main_department\": 1,\n" +
                        "    \"extattr\": {\n" +
                        "        \"attrs\": [\n" +
                        "            {\n" +
                        "                \"type\": 0,\n" +
                        "                \"name\": \"文本名称\",\n" +
                        "                \"text\": {\n" +
                        "                    \"value\": \"文本\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"type\": 1,\n" +
                        "                \"name\": \"网页名称\",\n" +
                        "                \"web\": {\n" +
                        "                    \"url\": \"http://www.test.com\",\n" +
                        "                    \"title\": \"标题\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"external_position\": \"工程师\",\n" +
                        "    \"external_profile\": {\n" +
                        "        \"external_corp_name\": \"\",\n" +
                        "        \"external_attr\": [\n" +
                        "            {\n" +
                        "                \"type\": 0,\n" +
                        "                \"name\": \"文本名称\",\n" +
                        "                \"text\": {\n" +
                        "                    \"value\": \"文本\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"type\": 1,\n" +
                        "                \"name\": \"网页名称\",\n" +
                        "                \"web\": {\n" +
                        "                    \"url\": \"http://www.test.com\",\n" +
                        "                    \"title\": \"标题\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"type\": 2,\n" +
                        "                \"name\": \"测试app\",\n" +
                        "                \"miniprogram\": {\n" +
                        "                    \"appid\": \"wx8bd80126147dFAKE\",\n" +
                        "                    \"pagepath\": \"/index\",\n" +
                        "                    \"title\": \"miniprogram\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}\n").log().all().
                post("https://qyapi.weixin.qq.com/cgi-bin/user/update").
                then().
                log().all().extract().response();
        return res;
    }

    /**
     * @Description: 删除成员（仅通讯录同步助手或第三方通讯录应用可调用。
     * 若是绑定了腾讯企业邮，则会同时删除邮箱帐号。）
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response deleteMember(String userId){
        Response res = given().filter(new TokenFilter()).log().all().
                queryParam("userid",userId).
                get("https://qyapi.weixin.qq.com/cgi-bin/user/delete").
                then().
                log().all().extract().response();
        return res;
    }

    /**
     * @Description: 批量删除成员（仅通讯录同步助手或第三方通讯录应用可调用。
     * 若是绑定了腾讯企业邮，则会同时删除邮箱帐号。）
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response deleteMemberList(ArrayList<String> useridlist){
        Map<String,ArrayList<String>> map = new HashMap<>();
        map.put("useridlist",useridlist);
        Response res = given().filter(new TokenFilter()).log().all().
                contentType("application/json;charset=utf-8").
                body(map).
                post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete").
                then().log().all().
                extract().response();
        return res;
    }

    /**
     * @Description:获取部门成员
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @Test
    public static Response getDepartmentUserList(Long department_id,int fetch_child){
        Map<String,Object> map = new HashMap<>();
        map.put("department_id",department_id);
        map.put("fetch_child",fetch_child);
        Response res= given().filter(new TokenFilter()).log().all().
                queryParams(map).
                get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist").
                then().log().all().extract().response();
        return res;
    }

    /**
     * @Description:获取部门成员详情
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @Test
    public static Response getDepartmentUserInfo(Long department_id,int fetch_child){
        Map<String,Object> map = new HashMap<>();
        map.put("department_id",department_id);
        map.put("fetch_child",fetch_child);
        Response res= given().filter(new TokenFilter()).log().all().
                queryParams(map).
                get("https://qyapi.weixin.qq.com/cgi-bin/user/list").
                then().log().all().extract().response();
        return res;
    }
}
