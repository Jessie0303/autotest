package junit5;

import util.Calculator;
import org.junit.Assert;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class AssumptionsDemo {
    private final Calculator calculator = new Calculator();

    @Test
    void testOnlyOnCiServer() {
        Assumptions.assumeTrue("CI".equals(System.getenv("ENV")));
        // remainder of test
    }

    @Test
    void testOnlyOnDeveloperWorkstation() {
        Assumptions.assumeTrue("DEV".equals(System.getenv("ENV")),
                () -> "Aborting test: not on developer workstation");
        // remainder of test
    }

    @Test
    void testInAllEnvironments() throws InterruptedException {
        Assumptions.assumingThat("CI".equals(System.getenv("ENV")),
                () -> {
                    // perform these assertions only on the CI server
                    Assert.assertEquals(2, calculator.divide(4, 2));
                });

        // perform these assertions in all environments
        Assert.assertEquals(42, calculator.multiply(6, 7));
    }
}
