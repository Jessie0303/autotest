package wework.api.contact;

import io.restassured.response.Response;
import wework.filter.TokenFilter;
import wework.utils.FakerUtils;

import static io.restassured.RestAssured.given;

public class DepartmentApi {
    static String suffix = FakerUtils.getTimeStamp();
    /**
     * @Description:创建部门
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response createDepartment(String name,String name_en,String parentid,String order){
        Response createResponse = given().filter(new TokenFilter()).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "   \"name\": \""+name+suffix+"\",\n" +
                        "   \"name_en\": \""+name_en+suffix+"\",\n" +
                        "   \"parentid\": "+Long.valueOf(parentid)+",\n" +
                        "   \"order\": "+Integer.valueOf(order)+"\n"+
                        "}\n").
                post("https://qyapi.weixin.qq.com/cgi-bin/department/create").
                then().log().all().extract().response();
        return createResponse;
    }
    /**
     * @Description:更新部门
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response updateDepartment(String name,String name_en,String parentid,String order,Long departmentId){
        Response updateResponse=given().filter(new TokenFilter()).log().all().
                contentType("application/json; charset=UTF-8").
                body("{\n" +
                        "   \"id\": "+departmentId+",\n" +
                        "   \"name\": \""+name+suffix+"mdf\",\n" +
                        "   \"name_en\": \""+name_en+suffix+"mdf\",\n" +
                        "   \"parentid\": "+parentid+",\n" +
                        "   \"order\": "+Integer.valueOf(order)+"\n"+
                        "}\n").
                post("https://qyapi.weixin.qq.com/cgi-bin/department/update").
                then().log().all().
                extract().response();
        return updateResponse;
    }

    /**
     * @Description:删除部门
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response deleteDepartment(Long departmentId){
        Response deleteResponse = given().filter(new TokenFilter()).log().all().
                queryParam("id",departmentId).
                get("https://qyapi.weixin.qq.com/cgi-bin/department/delete").
                then().
                log().all().extract().response();
        return deleteResponse;
    }

    /**
     * @Description:获取该部门下所有子部门列表
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    public static Response getAllDepartmentInfo(Long parentid){
        Response getDepartmentList = given().filter(new TokenFilter()).log().all().
                queryParam("id",parentid).
                get("https://qyapi.weixin.qq.com/cgi-bin/department/list").
                then().
                log().all().extract().response();
        return getDepartmentList;
    }
}
