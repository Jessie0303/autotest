package wework.testcases.contact;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wework.api.contact.DepartmentApi;
import wework.filter.TokenFilter;
import wework.task.ClearTask;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentTest {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentTest.class);
    static Long parentId= Long.valueOf(1);
    TokenFilter tokenFilter = new TokenFilter();
    @BeforeEach
    @AfterEach
    public void clear(){
        ClearTask.clearDepartment(parentId);
    }

    /**
     * @Description:创建部门
     * @Author: JessieXu
     * @Date: 2021/6/4
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/DepartmentTestcases/createDepartment.csv",numLinesToSkip = 1)
    public void createDepartment(String name,String name_en,String parentid,String order,String statusCode,String errcode,String errmsg){
        Response res = DepartmentApi.createDepartment(name,name_en,parentid,order);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/DepartmentTestcases/updateDepartment.csv",numLinesToSkip = 1)
    public void updateDepartment(String name,String name_en,String parentid,String order,String statusCode,String errcode,String errmsg){
        Long departmentId = Long.valueOf(DepartmentApi.createDepartment(name,name_en,parentid,order).path("id").toString());
        Response res = DepartmentApi.updateDepartment(name,name_en,parentid,order,departmentId);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/DepartmentTestcases/deleteDepartment.csv",numLinesToSkip = 1)
    public void deleteDepartment(String name,String name_en,String parentid,String order,String statusCode,String errcode,String errmsg){
        Long departmentId = Long.valueOf(DepartmentApi.createDepartment(name,name_en,parentid,order).path("id").toString());
        Response res = DepartmentApi.deleteDepartment(departmentId);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/DepartmentTestcases/getAllDepartment.csv",numLinesToSkip = 1)
    public void getAllDepartmentInfo(String name,String name_en,String parentid,String order,String statusCode,String errcode,String errmsg){
        Long departmentId = Long.valueOf(DepartmentApi.createDepartment(name,name_en,parentid,order).path("id").toString());
        Response res = DepartmentApi.getAllDepartmentInfo(parentId);

        ArrayList<Object> list = res.path("department.id");
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg")),
                ()->
                {
                    if(departmentId<=2147483647)
                    {
                        assertThat(list,hasItem(departmentId.intValue()));
                    }else
                    {
                        assertThat(list,hasItem(departmentId.longValue()));
                    }
                }
        );
    }
}
