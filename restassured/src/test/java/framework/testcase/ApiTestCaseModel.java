package framework.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import framework.steps.StepModel;
import framework.steps.StepResult;
import wework.utils.FakerUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
@Data
public class ApiTestCaseModel {
    public static final Logger logger = LoggerFactory.getLogger(ApiTestCaseModel.class);

    private String name;
    private String description;
    private ArrayList<StepModel> steps;
    private ArrayList<Executable> assertList =  new ArrayList<>();
    private HashMap<String,String> testCaseVariables =  new HashMap<>();

    public static ApiTestCaseModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path), ApiTestCaseModel.class);
    }
    public void run(){
        /**
         * 1、加载用例层保留关键字变量
         */
        this.testCaseVariables.put("getTimeStamp", FakerUtils.getTimeStamp());
        logger.info("用力变量更新： "+testCaseVariables);

        /**
         * 2、遍历执行step
         */
        steps.forEach(step->{
            StepResult stepResult =  step.run(testCaseVariables);
            /**
             * 3、处理save变量
             */
            if(stepResult.getStepVariables().size()>0){
                testCaseVariables.putAll(stepResult.getStepVariables());
                logger.info("testcase变量更新： " +testCaseVariables);
            }
            /**
             * 4、追加每次的断言
             */
            if(stepResult.getAssertList().size()>0){
                assertList.addAll(stepResult.getAssertList());
            }
        });
        /**
         * 5、统一断言
         */
        assertAll("",assertList.stream());
    }
}
