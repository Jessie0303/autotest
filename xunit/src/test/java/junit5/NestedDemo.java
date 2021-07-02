package junit5;

import org.junit.jupiter.api.*;

public class NestedDemo {
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
    public void testcase1(){
        System.out.println("testcase1测试");
    }

    @Nested
    @DisplayName("这是MySecondNestedClass内嵌类的DisplayName")
    class MySecondNestedClass{
        @Test
        @DisplayName("这是secondClassCase1测试方法DisplayName")
        public void secondClassCase1(){
            System.out.println("secondClassCase1测试");
        }
    }

    @Nested
    @Disabled
    @DisplayName("这是MyThirdNestedClass内嵌类的DisplayName")
    class MyThirdNestedClass{
        @Test
        @DisplayName("这是thirdClassCase1测试方法DisplayName")
        public void thirdClassCase1(){
            System.out.println("thirdClassCase1测试");
        }
    }

    @Nested
    @DisplayName("这是MyFourthNestedClass内嵌类的DisplayName")
    class MyFourthNestedClass{
        @Test
        @DisplayName("这是fourthClassCase1测试方法DisplayName")
        public void fourthClassCase1(){
            System.out.println("fourthClassCase1测试");
        }
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
