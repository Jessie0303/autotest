package framework.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Data;
import framework.actions.ApiActionModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
/***
* @Description: 接口对象类
* @Author: JessieXu
* @Date: 2021/6/22
*/
@Data
public class ApiObjectModel {
    private String name;
    private HashMap<String, ApiActionModel> actions;

    public static ApiObjectModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiObjectModel.class);
    }
}
