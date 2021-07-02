package junit5;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
@Tag("model")
public class TaggingDemo {
    @Test
    @Tag("smoke")
    void testingTaxCalculation() {
        System.out.println("根据标签过滤要测试的内容");
    }
}
