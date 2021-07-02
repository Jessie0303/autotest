package testng;

import org.testng.*;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.Set;
@Test(groups={"demo-test"})
public class DependencyInjectionTest {
    @BeforeClass
    public void bfClass(ITestContext iTestContext) {
        ITestNGMethod[] iTestNGMethod = iTestContext.getAllTestMethods();
        for (ITestNGMethod iTestNGMethod1 : iTestNGMethod) {
            System.out.println("@BeforeClass-方法名称：" + iTestNGMethod1.getMethodName());
        }
    }

    @BeforeMethod
    public void bfMethod1(XmlTest xmlTest) {
        System.out.println("@BeforeMethod-test标签名称：" + xmlTest.getName());
    }

    @BeforeMethod
    public void bfMethod2(Method method) {
        System.out.println("@BeforeMethod-方法：" + method.getName());
    }

    @BeforeMethod
    public void bfMethod3(Object[] objects) {
        for (Object object : objects) {
            System.out.println("@BeforeMethod-对象：" + object.toString());
        }
    }

    @Test(dataProvider = "dp")
    public void test1(int i,int j) {
        System.out.println("@Test method test1()");
        Assert.assertEquals(i,j);
    }

    @Test(dataProvider = "dp")
    public void test2(int i, int j) {
        System.out.println("@Test method test2()");
        Assert.assertEquals(i, j);
    }

    @DataProvider(name = "dp")
    public Object[][] datapro(ITestContext iTestContext, Method method) {
        if (method.getName().equals("test2"))
            return new Object[][]{new Object[]{1, 3}};
        else
            return new Object[][]{new Object[]{1, 1}};
    }

//    @AfterMethod
//    public void afMethod(@NoInjection ITestResult iTestResult) {
//        System.out.println("@AfterMethod-方法名称：" + iTestResult.getMethod().getMethodName() + " 执行状态（1-成功，2-失败，3-skip）：" + iTestResult.getStatus());
//    }

    @AfterClass
    public void afClass(ITestContext iTestContext) {
        //成功结果
        IResultMap iResultMapPass = iTestContext.getPassedTests();
        Set<ITestResult> iResultMapSetPass = iResultMapPass.getAllResults();
        for (ITestResult iTestResultPass : iResultMapSetPass) {
            System.out.println("@AfterClass-" + iTestResultPass.getMethod().getMethodName() + " 方法执行状态（1-成功，2-失败，3-skip）：" + iTestResultPass.getStatus());
        }

        //失败结果
        IResultMap iResultMapFail = iTestContext.getFailedTests();
        Set<ITestResult> iResultMapSetFail = iResultMapFail.getAllResults();
        for (ITestResult iTestResultFail : iResultMapSetFail) {
            System.out.println("@AfterClass-" + iTestResultFail.getMethod().getMethodName() + " 方法执行状态（1-成功，2-失败，3-skip）：" + iTestResultFail.getStatus());
        }
    }
}