package wework.testcases.contact;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wework.api.contact.DepartmentApi;
import wework.api.contact.DepartmentMemberApi;
import wework.task.ClearTask;
import wework.utils.FakerUtils;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentMemberTest {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentMemberTest.class);
    static ClearTask clearTask = new ClearTask();
    static Long parentId= Long.valueOf(1);
    static Long departmentIdInit;
    String suffix = FakerUtils.getTimeStamp();

    @BeforeAll
    public static void init(){
        //新建一个部门
        departmentIdInit = Long.valueOf(DepartmentApi.createDepartment("测试部门成员专用","BMCYCS",parentId.toString(),"1").path("id").toString());
    }
    @AfterAll
    public static void clear(){
        clearTask.clearMemberDepartment(departmentIdInit);
    }
    /**
     * @Description: 创建成员(管理工具-通讯录同步)
     * @Author: JessieXu
     * @Date: 2021/6/3
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/MemberTestcases/createMember.csv",numLinesToSkip = 1)
    public void createMember(String username,String name,String ename,String statusCode,String errcode,String errmsg){
        String userid=username+suffix;
        Response res = DepartmentMemberApi.createMember(userid,name,ename,departmentIdInit);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description: 在通讯录同步助手中此接口可以读取企业通讯录的所有成员的信息，而自建应用可以读取该应用设置的可见范围内的成员信息。
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/MemberTestcases/getMemberInfo.csv",numLinesToSkip = 1)
    public void getMemberInfo(String username,String name,String ename,String statusCode,String errcode,String errmsg,String status){
        String userid=username+suffix;
        DepartmentMemberApi.createMember(userid,name,ename,departmentIdInit);
        Response res = DepartmentMemberApi.getMemberInfo(userid);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg")),
                ()->assertEquals(userid,res.path("userid")),
                ()->assertEquals(status,res.path("status").toString())
        );
    }

    /**
     * @Description:更新成员（仅通讯录同步助手或第三方通讯录应用可调用。）
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/MemberTestcases/updateMember.csv",numLinesToSkip = 1)
    public void updateMember(String username,String name,String ename,String statusCode,String errcode,String errmsg,String newname){
        String userid=username+suffix;
        DepartmentMemberApi.createMember(userid,name,ename,departmentIdInit);
        Response res = DepartmentMemberApi.updateMember(userid,newname,departmentIdInit);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description: 删除成员（仅通讯录同步助手或第三方通讯录应用可调用。
     * 若是绑定了腾讯企业邮，则会同时删除邮箱帐号。）
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/MemberTestcases/deleteMember.csv",numLinesToSkip = 1)
    public void deleteMember(String username,String name,String ename,String statusCode,String errcode,String errmsg){
        String userid=username+suffix;
        DepartmentMemberApi.createMember(userid,name,ename,departmentIdInit);
        Response res = DepartmentMemberApi.deleteMember(userid);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description: 批量删除成员（仅通讯录同步助手或第三方通讯录应用可调用。
     * 若是绑定了腾讯企业邮，则会同时删除邮箱帐号。）
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/MemberTestcases/deleteMemberList.csv",numLinesToSkip = 1)
    public void deleteMemberList(String username,String name,String ename,String statusCode,String errcode,String errmsg){
        String[] userList = username.split("、");
        ArrayList<String> idList = new ArrayList<>();
        for(String user:userList){
            String userid=user+suffix;
            DepartmentMemberApi.createMember(userid,name,ename,departmentIdInit);
            idList.add(userid);
        }
        Response res = DepartmentMemberApi.deleteMemberList(idList);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:获取部门成员
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/MemberTestcases/getDepartmentMemberList.csv",numLinesToSkip = 1)
    public void getDepartmentMemberList(String username,String name,String ename,String department_id,String fetch_child,String statusCode,String errcode,String errmsg){
        String userid=username+suffix;
        DepartmentMemberApi.createMember(userid,name,ename,departmentIdInit);
        Response res = DepartmentMemberApi.getDepartmentUserList(Long.valueOf(department_id),Integer.valueOf(fetch_child));
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
    * @Description:获取部门成员详情
    * @Author: JessieXu
    * @Date: 2021/6/4
    */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/MemberTestcases/getDepartmentMemberInfo.csv",numLinesToSkip = 1)
    public void getDepartmentMemberInfo(String username,String name,String ename,String department_id,String fetch_child,String statusCode,String errcode,String errmsg){
        String userid=username+suffix;
        DepartmentMemberApi.createMember(userid,name,ename,departmentIdInit);
        Response res = DepartmentMemberApi.getDepartmentUserInfo(Long.valueOf(department_id),Integer.valueOf(fetch_child));
        ArrayList<Object> userlist = res.body().path("userlist.userid");
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg")),
                ()->assertThat(userlist,hasItem(userid))
        );
    }
}
