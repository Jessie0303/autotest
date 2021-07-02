package demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/*
 * ceshiren搜索一个帖子并点进去进行查看
 * */
public class SearchTest {
    static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void search(){
        String actTitle = "actTitle";
        String title = "title";
        // 进入测试人社区
        driver.get("https://ceshiren.com/");
        // 点击搜索按钮，等待搜索框出现后输入搜索项
        try{
            driver.findElement(By.cssSelector("#search-button")).click();
            By searchLoc = By.cssSelector("#search-term");
            WebDriverWait wait = new WebDriverWait(driver,3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchLoc));
            driver.findElement(searchLoc).sendKeys("Selenium");
            // 根据搜索项过滤出满足条件的帖子，当帖子数大于0时，选择第一个
            List<WebElement> topicLst = driver.findElements(By.cssSelector(".topic-title"));
            if(topicLst.size() > 0){
                title = topicLst.get(0).getText();
                topicLst.get(0).click();
                // 进入第一个帖子，等待帖子标题显示，获取帖子标题
                By topicTitleLoc = By.cssSelector(".fancy-title");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".fancy-title")));
                actTitle = driver.findElement(topicTitleLoc).getText();
                // 帖子标题与点击项标题对比，相同则成功
                assertThat("期待结果和实际结果不同！",actTitle,equalTo(title));
            }else{
                assertThat("无满足搜索条件的帖子",false);
            }
        }catch(Exception e){
            e.printStackTrace();
            assertThat("存在异常",false);
        }
    }

    @AfterAll
    public static void teardown(){
        driver.quit();
    }
}
