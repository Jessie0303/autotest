package wework.testcases.contact;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import wework.api.contact.DepartmentApi;
import wework.api.contact.DepartmentMemberApi;
import wework.api.contact.TagApi;
import wework.filter.TokenFilter;
import wework.task.ClearTask;
import wework.utils.FakerUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagTest {
    TokenFilter tokenFilter = new TokenFilter();
    String suffix = FakerUtils.getTimeStamp();
    @BeforeEach
    @AfterEach
    public void clear(){
        ClearTask.clearTags(tokenFilter);
    }
    /**
     * @Description:创建标签
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/TagTestcases/createTag.csv",numLinesToSkip = 1)
    public void createTag(String name,String statusCode,String errcode,String errmsg){
        Response res = TagApi.createTag(tokenFilter,name);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description: 更新标签名字
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/TagTestcases/updateTag.csv",numLinesToSkip = 1)
    public void updateTag(String tagName,String statusCode,String errcode,String errmsg){

        int tagId = TagApi.createTag(tokenFilter,tagName).path("tagid");
        Response res = TagApi.updateTag(tokenFilter,tagId,tagName);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description: 删除标签
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/TagTestcases/deleteTag.csv",numLinesToSkip = 1)
    public void deleteTag(String tagName,String statusCode,String errcode,String errmsg){
        int tagId = TagApi.createTag(tokenFilter,tagName).path("tagid");
        Response res = TagApi.deleteTag(tokenFilter,tagId);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:获取标签成员
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/TagTestcases/getTagMember.csv",numLinesToSkip = 1)
    public void getTagMember(String tagName,String statusCode,String errcode,String errmsg){
        int tagId = TagApi.createTag(tokenFilter,tagName).path("tagid");
        Response res = TagApi.getTagMember(tokenFilter,tagId);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:增加标签成员
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/TagTestcases/addTagMember.csv",numLinesToSkip = 1)
    public void addTagMember(String tagName,String username,String name,String ename,String partyName,String partyNameEn,String parentId,String order,String statusCode,String errcode,String errmsg){
        int tagId = TagApi.createTag(tokenFilter,tagName).path("tagid");
        Long department = Long.valueOf(DepartmentApi.createDepartment(partyName,partyNameEn,parentId,order).path("id").toString());
        String[] userIds = username.split("、");
        String[] userNames = name.split("、");
        String[] userEnNames = ename.split("、");
        ArrayList<String> userList = new ArrayList();
        ArrayList<Long> partyList = new ArrayList<>();
        partyList.add(department);
        try{
            int size = userIds.length;
            if(size > 0){
                for(int i=0;i<size;i++){
                    String uid = userIds[i]+suffix;
                    userList.add(uid);
                    DepartmentMemberApi.createMember(uid,userNames[i],userEnNames[i],department);
                }
            }
        }catch (Exception e){
            System.out.println("请确认测试用例新增成员的id、name、ename正确");
        }
        System.out.println("here");
        Response res = TagApi.addTagMember(tokenFilter,tagId,userList,partyList);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:删除标签成员
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/TagTestcases/delTagMember.csv",numLinesToSkip = 1)
    public void delTagMember(String tagName,String username,String name,String ename,String partyName,String partyNameEn,String parentId,String order,String statusCode,String errcode,String errmsg){
        int tagId = TagApi.createTag(tokenFilter,tagName).path("tagid");
        Long department = Long.valueOf(DepartmentApi.createDepartment(partyName,partyNameEn,parentId,order).path("id").toString());
        String[] userIds = username.split("、");
        String[] userNames = name.split("、");
        String[] userEnNames = ename.split("、");
        ArrayList<String> userList = new ArrayList();
        ArrayList<Long> partyList = new ArrayList<>();
        partyList.add(department);
        try{
            int size = userIds.length;
            if(size > 0){
                for(int i=0;i<size;i++){
                    String uid = userIds[i]+suffix;
                    userList.add(uid);
                    DepartmentMemberApi.createMember(uid,userNames[i],userEnNames[i],department);
                }
            }
        }catch (Exception e){
            System.out.println("请确认测试用例新增成员的id、name、ename正确");
        }
        TagApi.addTagMember(tokenFilter,tagId,userList,partyList);
        Response res = TagApi.delTagMember(tokenFilter,tagId,userList,partyList);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:获取标签列表
     * @Author: JessieXu
     * @Date: 2021/6/17
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/TagTestcases/getTagList.csv",numLinesToSkip = 1)
    public void getTagList(String statusCode,String errcode,String errmsg){
        Response res = TagApi.getTagList(tokenFilter);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }
}
