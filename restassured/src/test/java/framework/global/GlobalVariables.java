package framework.global;

import java.util.HashMap;

/***
* @Description: 从来存储全局变量
* @Author: JessieXu
* @Date: 2021/6/22
*/
public class GlobalVariables {
    static private HashMap<String,String> globalVariables = new HashMap<>();

    public static HashMap<String,String> getGlobalVariables(){
        return globalVariables;
    }

    public static void setGlobalVariables(HashMap<String,String> globalVars){

        globalVariables = globalVars;
    }
}
