package framework.steps;

import io.restassured.response.Response;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import framework.global.ApiLoader;
import framework.global.GlobalVariables;
import util.PlaceholderUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class StepModel {
    public static final Logger logger = LoggerFactory.getLogger(StepModel.class);

    private String api;
    private String action;
    private ArrayList<String> actualParameter;
    private ArrayList<AssertModel> asserts;
    private HashMap<String,String> save;
    private HashMap<String,String> saveGlobal;

    private ArrayList<String> finalActualParameter =  new ArrayList<>();
    private HashMap<String,String> stepVariable = new HashMap<>();
    private StepResult stepResult =  new StepResult();
    private ArrayList<Executable> assertList = new ArrayList<>();

    public StepResult run(HashMap<String,String> testCaseVariables){
        /**
         * 1、实参占位符替换
         */
        if(actualParameter != null){
            finalActualParameter.addAll(PlaceholderUtils.resolveList(actualParameter,testCaseVariables));
        }
        /**
         * 2、执行action
         */
        Response response = ApiLoader.getAction(api,action).run(finalActualParameter);

        /**
         * 3、存save
         */
        if(save !=null){
            save.forEach((variablesName,path)->{
                String value = response.path(path).toString();
                stepVariable.put(variablesName,value);
                logger.info("step变量更新： "+stepVariable);
            });
        }
        /**
         * 4、存saveGloble
         */
        if(saveGlobal !=null){
            saveGlobal.forEach((variablesName,path)->{
                String value = response.path(path.toString());
                GlobalVariables.getGlobalVariables().put(variablesName,value);
                logger.info("全局变量更新： "+GlobalVariables.getGlobalVariables());
            });
        }
        /**
         * 5、存储断言结果
         */
        if(asserts != null){
            asserts.stream().forEach(assertModel -> {
//                assertList.add(()->{assertThat(assertModel.getReason(),response.path(assertModel.getActual().toString()),equalTo(assertModel.getExpect()));});
            });
        }
        /**
         * 8、组装result
         */
        stepResult.setAssertList(assertList);
        stepResult.setStepVariables(stepVariable);
        return  stepResult;
    }
}
