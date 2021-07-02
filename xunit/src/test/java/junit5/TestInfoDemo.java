package junit5;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/*
* TestInfoParameterResolver：如果构造函数或方法参数的类型为 TestInfo，
* TestInfoParameterResolver则将提供TestInfo 与当前容器或test对应的实例作为该参数的值。
* 所述 TestInfo然后可以被用于检索有关当前容器或测试信息，如显示名称，测试类，测试方法，以及相关的标签。
* 显示名称可以是技术名称（例如测试类或测试方法的名称），也可以是通过进行配置的自定义名称@DisplayName。
* */
@DisplayName("TestInfo Demo")
public class TestInfoDemo {

    TestInfoDemo(TestInfo testInfo) {
        Assert.assertEquals("TestInfo Demo", testInfo.getDisplayName());
    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        Assert.assertTrue(displayName.equals("TEST 1") || displayName.equals("test2()"));
    }

    @Test
    @DisplayName("TEST 1")
    @Tag("my-tag")
    void test1(TestInfo testInfo) {
        Assert.assertEquals("TEST 1", testInfo.getDisplayName());
        Assert.assertTrue(testInfo.getTags().contains("my-tag"));
    }

    @Test
    void test2() {
    }

}
