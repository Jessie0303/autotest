package junit5;

import org.junit.jupiter.api.*;


public class AssertDemo {
    //只能修饰静态方法
    @BeforeAll
    public static void init(){
        System.out.println("初始化……");
    }

    @BeforeEach
    public void setup(){

        System.out.println("----------------------测试开始----------------------");
    }
    @Test
    public void printl(){
        System.out.println("test");
    }

    @AfterEach
    public void teardown(){
        System.out.println("----------------------测试结束----------------------");
    }

    //只能修饰静态方法
    @AfterAll
    public static void cleanup(){
        System.out.println("清理数据");
    }
}
