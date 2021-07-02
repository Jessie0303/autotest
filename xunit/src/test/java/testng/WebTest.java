package testng;

import org.testng.annotations.Test;

public class WebTest {
    private  int m_numberOfTimes;
    public WebTest(int numberOfTimes){
        m_numberOfTimes = numberOfTimes;
    }

    @Test
    public void testServer(){
        int result = 0;
        for(int i=0;i<m_numberOfTimes;i++){
            result+=i;
        }
        System.out.println(result);
    }
}
