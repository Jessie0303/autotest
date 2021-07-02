package testng;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Test(groups={"demo-test"})
public class FactoryWebTest {
    @Factory
    public Object[] createInstances(){
        Object[] result = new Object[10];
        for(int i=0;i<10;i++){
            result[i] = new WebTest(i*10);
        }
        return result;
    }
}

