package junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ExecuteConditionDemo {

    /*
    * 操作系统条件
    * */
    @Test
    @EnabledOnOs(OS.MAC)
    void onlyOnMacOs(){
        System.out.println("只有MacOS系统才执行");
    }

    @Test
    @EnabledOnOs({OS.LINUX,OS.MAC})
    void OnMacOrLinux(){
        System.out.println("MacOS或者Linux系统才执行");
    }

    @Test
    @DisabledOnOs({OS.WINDOWS})
    void notOnWindows(){
        System.out.println("Windows系统不执行");
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @EnabledOnOs(OS.MAC)
    @interface TestOnMac{

    }
    @TestOnMac
    void myDefine(){
        System.out.println("自定义的注解");
    }

    /*
     * Java运行时环境条件
     * */
    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void onlyOnJava8(){
        System.out.println("只有Java8才执行");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_8,JRE.JAVA_9})
    void onlyOnJava8Or9(){
        System.out.println("Java8或9才执行");
    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_8,max = JRE.JAVA_11)
    void onlyOnJava8To11(){
        System.out.println("Java8到11都可执行");
    }

    //单独只指定min或max时，min不能等于JRE.JAVA_8，max不能等于JRE.OTHER
    @Test
    @EnabledForJreRange(min=JRE.JAVA_9)
    void onlyOnJava8Min(){
        System.out.println("Java8以上都可执行");
    }

    @Test
    @EnabledForJreRange(max=JRE.JAVA_11)
    void onlyOnJava11Max(){
        System.out.println("Java11以下都可执行");
    }

    @Test
    @DisabledForJreRange(max=JRE.JAVA_8)
    void notOnJava8Below(){
        System.out.println("Java8以下不可执行");
    }

    /*
     * 系统属性条件
     * 释基于JVM系统属性的值来启用或禁用容器或测试
     * */

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {
        System.out.println("64位系统可执行");
    }

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer() {
        System.out.println("不可在持续集成服务器上执行");
    }

    /*
     * 环境变量条件
     * 可根据环境变量的值来启用或禁用容器或测试
     * */
    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    void onlyOnStagingServer() {
        System.out.println("只能在生产镜像服务器上执行");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
    void notOnDeveloperWorkstation() {
        System.out.println("不可在开发环境上执行");
    }

    /*
     * 自定义条件
     * 该方法通过其名称或全限定名（如果位于测试类之外）提供给注释
     * */
    @Test
    @EnabledIf("customCondition")
    void enabled() {
        System.out.println("自定义条件返回为true时执行");
    }

    @Test
    @DisabledIf("customCondition")
    void disabled() {
        System.out.println("自定义条件返回为true时不执行");
    }

    boolean customCondition() {
        return true;
    }
}
