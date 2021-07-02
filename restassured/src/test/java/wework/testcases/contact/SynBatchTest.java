package wework.testcases.contact;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import wework.api.contact.SynBatchApi;
import wework.api.contact.TagApi;
import wework.filter.TokenFilter;
import wework.utils.FakerUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SynBatchTest {
    public static final Logger logger = LoggerFactory.getLogger(SynBatchTest.class);
    TokenFilter tokenFilter = new TokenFilter();
    String suffix = FakerUtils.getTimeStamp();
    String baseDir = "/Users/cici/files/workdoc_xj/java_demo/hwork/src/main/resources/data/weworkapi/testcases/contact/SynBatchTestcases/";
    String filename = baseDir+"batch_party_load.csv";
    String media_id = SynBatchApi.uploadTemp(tokenFilter,filename).path("media_id");
    /**
     * @Description:增量更新成员
     * @Author: JessieXu
     * @Date: 2021/6/18
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/SynBatchTestcases/increaseSynUser.csv",numLinesToSkip = 1)
    public void increaseSynUser(String toInvite,String statusCode,String errcode,String errmsg){
        String filename = baseDir+"increment_load.csv";
        String media_id = SynBatchApi.uploadTemp(tokenFilter,filename).path("media_id");
        boolean to_invite = Boolean.valueOf(toInvite);
        Response res = SynBatchApi.increaseSynUser(tokenFilter,media_id,to_invite);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:全量覆盖成员
     * @Author: JessieXu
     * @Date: 2021/6/18
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/SynBatchTestcases/coverSynUser.csv",numLinesToSkip = 1)
    public void coverSynUser(String toInvite,String statusCode,String errcode,String errmsg){
        String filename = baseDir+"cover_member_load.csv";
        String media_id = SynBatchApi.uploadTemp(tokenFilter,filename).path("media_id");
        boolean to_invite = Boolean.valueOf(toInvite);
        Response res = SynBatchApi.coverSynUser(tokenFilter,media_id,to_invite);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:全量覆盖部门
     * @Author: JessieXu
     * @Date: 2021/6/18
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/SynBatchTestcases/coverDepartment.csv",numLinesToSkip = 1)
    public void coverDepartment(String statusCode,String errcode,String errmsg){
        Response res = SynBatchApi.coverDepartment(tokenFilter,media_id);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }

    /**
     * @Description:获取异步任务结果
     * @Author: JessieXu
     * @Date: 2021/6/18
     */
    @ParameterizedTest
    @CsvFileSource(resources="/data/weworkapi/testcases/contact/SynBatchTestcases/getResult.csv",numLinesToSkip = 1)
    public void getResult(String statusCode,String errcode,String errmsg){
        String jobid = SynBatchApi.coverDepartment(tokenFilter,media_id).path("jobid");
        Response res = SynBatchApi.getResult(tokenFilter,jobid);
        assertAll("返回值校验",
                ()->assertEquals(statusCode,String.valueOf(res.statusCode())),
                ()->assertEquals(errcode,res.path("errcode").toString()),
                ()->assertEquals(errmsg,res.path("errmsg"))
        );
    }
}
