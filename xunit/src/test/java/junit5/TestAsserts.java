package junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestAsserts {
    //只能修饰静态方法
    @BeforeAll
    public static void init(){
        System.out.println("初始化……");
    }

    @BeforeEach
    public void setup(){
        System.out.println("----------------------测试开始----------------------");
    }
//
//    @Test
//    @DisplayName("Lambda表达式断言")
//    void testGroupAssertions(){
//        int[] numbers = {0,1,2,3,4};
//        int[] expect = {0,1,2,3,4};
//        Assertions.assertAll("numbers",
//                ()->Assertions.assertArrayEquals(numbers,expect)
//        );
//        Assertions.assertAll("numbers",
//                ()->Assertions.assertEquals(numbers[0],0),
//                ()->Assertions.assertEquals(numbers[1],1),
//                ()->Assertions.assertEquals(numbers[2],2),
//                ()->Assertions.assertEquals(numbers[3],3)
//        );
//    }

//    @Test
//    @DisplayName("标准断言")
//    void standardAssertions() {
//        Assertions.assertEquals(2, 1+1);
//        Assertions.assertEquals(4, 2*2,
//                "The optional failure message is now the last parameter");
//        Assertions.assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "
//                + "to avoid constructing complex messages unnecessarily.");
//    }
//
//    @Test
//    @DisplayName("组断言")
//    void groupedAssertions() {
//        // In a grouped assertion all assertions are executed, and all
//        // failures will be reported together.
//        User user = new User();
//        user.set("username","Jenny01");
//        user.set("sex","女");
//        Assertions.assertAll("person",
//            () -> Assertions.assertEquals("Jane", user.getUsername()),
//            () -> Assertions.assertEquals("Doe", user.getSex())
//        );
//    }
//
//    @Test
//    @DisplayName("依赖断言")
//    void dependentAssertions() {
//        User user = new User();
//        user.setUsername("Tom");
//        user.setIdentity("Tom20210413");
//        // Within a code block, if an assertion fails the
//        // subsequent code in the same block will be skipped.
//        Assertions.assertAll("properties",
//            () -> {
//
//                String firstName = user.getUsername();
//                Assertions.assertNotNull(firstName);
//
//                // Executed only if the previous assertion is valid.
//                Assertions.assertAll("name",
//                    () -> Assertions.assertTrue(firstName.startsWith("T")),
//                    () -> Assertions.assertTrue(firstName.endsWith("m"))
//                );
//            },
//            () -> {
//                // Grouped assertion, so processed independently
//                // of results of first name assertions.
//                String identity = user.getIdentity();
//                Assertions.assertNotNull(identity);
//
//                // Executed only if the previous assertion is valid.
//                Assertions.assertAll("identity",
//                    () -> Assertions.assertTrue(identity.startsWith("T")),
//                    () -> Assertions.assertTrue(identity.endsWith("3"))
//                );
//            }
//        );
//    }
//
//    @Test
//    void exceptionTesting() {
//        Calculator calculator = new Calculator();
//        Exception exception = Assertions.assertThrows(ArithmeticException.class, () ->
//            calculator.divide(1, 0));
//        Assertions.assertEquals("/ by zero", exception.getMessage());
//    }
//
//    @Test
//    void timeoutNotExceeded() {
//        // The following assertion succeeds.
//        Assertions.assertTimeout(Duration.ofMinutes(2), () -> {
//            // Perform task that takes less than 2 minutes.
//        });
//    }
//
//    @Test
//    void timeoutNotExceededWithResult() {
//        // The following assertion succeeds, and returns the supplied object.
//        String actualResult = Assertions.assertTimeout(Duration.ofMinutes(2), () -> {
//            return "a result";
//        });
//        Assertions.assertEquals("a result", actualResult);
//    }
//
//    @Test
//    void timeoutExceeded() {
//        // The following assertion fails with an error message similar to:
//        // execution exceeded timeout of 10 ms by 91 ms
//        Assertions.assertTimeout(Duration.ofMillis(10), () -> {
//            // Simulate task that takes more than 10 ms.
//            Thread.sleep(100);
//        });
//    }
//
//    @Test
//    void timeoutExceededWithPreemptiveTermination() {
//        // The following assertion fails with an error message similar to:
//        // execution timed out after 10 ms
//        Assertions.assertTimeoutPreemptively(Duration.ofMillis(10), () -> {
//            // Simulate task that takes more than 10 ms.
//            new CountDownLatch(1).await();
//        });
//    }
//
//    @Test
//    @DisplayName("超时操作测试")
//    void test_limit_1_second(){
//        Assertions.assertTimeoutPreemptively(Duration.of(1, ChronoUnit.SECONDS),
//                ()->Thread.sleep(2000)
//        );
//    }
//
//    @Test
//    @DisplayName("测试捕获的异常")
//    void assertThrowsException(){
//            Assertions.assertThrows(IllegalArgumentException.class,
//                    ()->{
//                Integer.valueOf("wo");
//            } );
//    }

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
