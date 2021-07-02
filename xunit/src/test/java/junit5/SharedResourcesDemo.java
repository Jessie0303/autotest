package junit5;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.Properties;

/*
* 当使用@ResourceLock批注声明对共享资源的访问时，JUnit Jupiter引擎将使用此信息来确保不会并行运行任何冲突的测试。
* */
@Execution(ExecutionMode.CONCURRENT)
public class SharedResourcesDemo {
    private Properties backup;

    @BeforeEach
    void backup() {
        backup = new Properties();
        backup.putAll(System.getProperties());
    }

    @AfterEach
    void restore() {
        System.setProperties(backup);
    }

    @Test
    @ResourceLock(value = "SYSTEM_PROPERTIES", mode = ResourceAccessMode.READ)
    void customPropertyIsNotSetByDefault() {
        Assert.assertNull(System.getProperty("my.prop"));
    }

    @Test
    @ResourceLock(value = "SYSTEM_PROPERTIES", mode = ResourceAccessMode.READ_WRITE)
    void canSetCustomPropertyToApple() {
        System.setProperty("my.prop", "apple");
        Assert.assertEquals("apple", System.getProperty("my.prop"));
    }

    @Test
    @ResourceLock(value = "SYSTEM_PROPERTIES", mode = ResourceAccessMode.READ_WRITE)
    void canSetCustomPropertyToBanana() {
        System.setProperty("my.prop", "banana");
        Assert.assertEquals("banana", System.getProperty("my.prop"));
    }
}
