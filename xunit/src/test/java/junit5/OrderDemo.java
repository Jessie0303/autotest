package junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestMethodOrder(OrderAnnotation.class)
public class OrderDemo {
    //只能修饰静态方法
    @BeforeAll
    public static void init(){
        System.out.println("初始化……");
    }
    //只能修饰静态方法
    @BeforeEach
    public void setup(){
        System.out.println("----------------------测试开始----------------------");
    }
    //只能修饰静态方法

    static Stream<String> memberProvider(){
        return Stream.of("徐一","徐二","徐三");
    }


    @ParameterizedTest
    @MethodSource("memberProvider")
    @Order(3)
    @DisplayName("这是testcase1测试方法DisplayName")
    public void testcase1(String s){
        System.out.println("testcase1测试"+s);
    }

    @ParameterizedTest
    @MethodSource("memberProvider")
    @Order(2)
    public void testcase2(String s){
        System.out.println("testcase2测试"+s);
    }

    @ParameterizedTest
    @MethodSource("memberProvider")
    @Order(1)
    public void testcase3(String s){
        System.out.println("testcase3测试"+s);
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
