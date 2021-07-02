package testng;

import util.Calculator;
import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.*;

@Listeners
@Test(groups={"demo-test"})
public class AnnotationTest {

//    @Factory(dataProvider="myFruitsSource")

    @BeforeSuite
    public void setUp(){
        System.out.println("这是BeforeSuite注解的方法");
    }

    @BeforeClass(alwaysRun = true)
    public void init(){
        System.out.println("这是BeforeClass注解的方法");
    }

    @BeforeTest
    public void start(){
        System.out.println("这是BeforeTest注解的方法");
    }

    @BeforeGroups
    public void initGroup(){
        System.out.println("这是BeforeGroups注解的方法");
    }

    @BeforeMethod
    public void initMethod(){
        System.out.println("这是BeforeMethod注解的方法");
    }

    @AfterMethod
    public void cleanMethod(){
        System.out.println("这是AfterMethod注解的方法");
    }

    @DataProvider(name="notParallel")
    public static Object[][] myFruitsSource(){
        Object[][] dataSource = new Object[10][];
        for(int i = 0;i < 10;i++){
            dataSource[i] = new String[5];
            String[] fruits = {"apple","banana","orange","strawberry","cherry"};
            for(int j = 0;j < 5;j++){
                dataSource[i][j] = fruits[j]+i+j;
            }
        }
        return dataSource;
    }

    @DataProvider(name="parallel")
    public static Object[][] myFriendsSource(){
        Object[][] dataSource = new Object[10][];
        for(int i = 0;i < 10;i++){
            dataSource[i] = new String[5];
            String[] friends = {"Eva","Diana","Fiona","Gigi","Henry"};
            for(int j = 0;j < 5;j++){
                dataSource[i][j] = friends[j]+i+j;
            }
        }
        return dataSource;
    }

    @Test(dataProvider = "parallel",enabled = true,alwaysRun = true,dependsOnMethods = {"testDemo2","testDemo3"})
    public void testDemo1(Object[] obj){
        System.out.println("这是Test注解的testDemo1方法，参数来源myFriendsSource");
        outPut(obj);
        Assert.assertTrue(true);
    }

    private static void outPut(Object[] obj){
        for(Object j:obj){
            System.out.print(j+" ");
        }
    }

    @Test(groups = {"smoke","back"},dataProvider = "notParallel")
    public void testDemo2(Object[] obj){
        System.out.println("这是Test注解的testDemo2方法，参数来源是myFruitsSource");
        outPut(obj);
        Assert.assertTrue(true);
    }

    @Test(enabled = true,dependsOnGroups = {"smoke"},invocationCount = 2,priority = 1)
    public void testDemo3(){
        System.out.println("这是Test注解的testDemo3方法");
        System.out.println("测试是否会被调用两次");
        Assert.assertTrue(true);
    }

//    @Parameters(value={"param1"})
    @Test
    public void testDemo4(){
        System.out.println("这是Test注解的testDemo4方法");
        Assert.assertTrue(true);
    }

    /*
    * 用于查找data Provider的类，
    * 如果不指定，默认当前测试方法所在的类或基类查找data Provider
    * 如果指定，data Provider要求是指定类的static方法
    * */
    @Test(dataProvider = "notParallel",dataProviderClass= AnnotationTest.class)
    public void testDemo5(Object[] obj){
        System.out.println("这是Test注解的testDemo4方法");
        outPut(obj);
        Assert.assertTrue(true);
    }

    /*
    * 测试方法期望抛出的异常列表，如果没有异常或者抛出异常不匹配，则该方法标记为失败
    * */
    @Test(expectedExceptions={TestException.class, NumberFormatException.class})
    public void testDemo6(){
        System.out.println("这是Test注解的testDemo4方法");
        String test = "一";
        int index = Integer.valueOf(test);
        Assert.assertEquals(index,1);
    }
    @Test(groups = {"Mac.checkintest"},successPercentage = 80,singleThreaded = true)
    public void testDemo7(){
        System.out.println("这是Test注解的testDemo7方法");
        int count = 0;
        while(count < 1000){
            count++;
        }
        Assert.assertEquals(1000,count);
    }

    /*
    * threadPoolSize代表线程数
    * invocationCount代表方法执行的次数
    * */
    @Test(timeOut = 1000,threadPoolSize = 10,invocationCount = 10)
    public void testDemo8() throws InterruptedException {
        System.out.println("这是Test注解的testDemo8方法");
        int result = Calculator.add(2,3);
        System.out.println("当前线程名为："+Thread.currentThread().getName());
        Assert.assertEquals(5,result);
    }

    @AfterGroups
    public void cleanGroup(){
        System.out.println("这是BeforeGroups注解的方法");
    }

    @AfterTest(description="在每个Test之后执行")
    public void end(){
        System.out.println("这是AfterTest注解的方法");
    }

    @AfterClass
    public void clean(){
        System.out.println("这是AfterClass注解的方法");
    }

    @AfterSuite
    public void teardown(){
        System.out.println("这是AfterSuite注解的方法");
    }





}
