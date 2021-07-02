package junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@DisplayName("这是MyFirstTestcase的测试类DisplayName")
public class MyFirstTestcase {

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
    @Order(6)
    @DisplayName("这是testcase1测试方法DisplayName")
    public void testcase1(){
        System.out.println("testcase1测试");
    }

    @Test
    @Disabled
    @Order(5)
    @DisplayName("这是testcase2被禁用╯°□°）╯")
    public void testcase2(){
        System.out.println("testcase2测试");
    }

    @Test
    @Order(4)
    @DisplayName("只有操作系统是macOs才会执行\uD83D\uDE31")
    @EnabledOnOs(OS.MAC)
    public void testcase3(){
        System.out.println("testcase3测试");
    }

    @Test
    @Order(3)
    @DisplayName("只有操作系统是Linux才会执行")
    @EnabledOnOs(OS.LINUX)
    public void testcase4(){
        System.out.println("testcase4测试");
    }

    @Test
    @Order(2)
    @DisplayName("这是重复测试")
    @RepeatedTest(value = 3,name="总共{totalRepetitions}次测试，这是{displayName}第{currentRepetition}次")
    public void testcase5(){
        System.out.println("testcase5测试");
    }

    @Test
    @Order(1)
    public void testcase6(){
        System.out.println("testcase6测试");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
    class MySecondNestedClass{
        @Test
        @DisplayName("这是MySecondNestedClass内嵌类的DisplayName的secondClassCase1方法")
        public void secondClassCase1(){
            System.out.println("secondClassCase1测试");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
    class MyThirdNestedClass{
        @Test
        @DisplayName("这是thirdClassCase1测试方法DisplayName")
        public void thirdClassCase1(){
            System.out.println("thirdClassCase1测试");
        }
    }

    @Nested
    @DisplayName("这是MyFourthNestedClass内嵌类的DisplayName")
    @org.junit.jupiter.api.IndicativeSentencesGeneration(separator = " -> ", generator = DisplayNameGenerator.ReplaceUnderscores.class)
    class MyFourthNestedClass{
        @Test
        @DisplayName("这是fourthClassCase1测试方法DisplayName")
        public void fourthClassCase1(){
            System.out.println("fourthClassCase1测试");
        }
    }

    @AfterEach
    void teardown(){
        System.out.println("----------------------测试结束----------------------");
    }
    //只能修饰静态方法
    @AfterAll
    static void cleanup(){
        System.out.println("清理数据");
    }
}
