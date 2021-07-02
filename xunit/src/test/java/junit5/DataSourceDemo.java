package junit5;

import entity.Book;
import entity.MyArgumentsProvider;
import entity.ToStringArgumentConverter;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class DataSourceDemo {

    // 最常用的数据源
    @ParameterizedTest
    @ValueSource(strings={"value1","value2","value3"})
    void strValueSource(String value){
        System.out.println(value);
    }
    // 枚举类
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class,names = {"DAYS","HOURS","SECONDS"})
    void testWithEnumSourceInclude(TimeUnit timeUnit){
        System.out.println(timeUnit);
    }

    // 方法名注入
    @ParameterizedTest
    @MethodSource("stringProvider")
    void addMemberMethodName(String name){
        System.out.println(name);
    }

    static Stream<String> stringProvider(){
        return Stream.of("cat","frog","fish");
    }

    @DisplayName("文件提供参数")
    @ParameterizedTest
    @CsvSource({"1,One","2,Two","3,Three"})
    void testDataFromCsv(long id,String name){
        System.out.printf("id:%d,name:%s;",id,name);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/data/DataSourceCsv.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromFile(String fruit, double quantity, double price) {
        System.out.println(fruit+"总价为:"+quantity*price);
        Assert.assertNotNull(fruit);
        Assert.assertNotEquals(0, quantity);
        Assert.assertNotEquals(0, price);
    }

    /*
    * @ArgumentsSource可用于指定自定义的，可重用的ArgumentsProvider。
    * 请注意，ArgumentsProvider必须将的实现声明为顶级类或static嵌套类。
    * */
    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider.class)
    void testWithArgumentsSource(String argument) {
        Assert.assertNotNull(argument);
    }

    /*
    * 如果a@ParameterizedTest声明了一个参数类型TimeUnit，
    * 而声明的源提供的实际类型是a String，则字符串将自动转换为相应的TimeUnit枚举常量。
    * */
    @ParameterizedTest
    @ValueSource(strings = "SECONDS")
    void testWithImplicitArgumentConversion(ChronoUnit argument) {
        Assert.assertNotNull(argument.name());
    }

    /*
    * 如果发现了多个工厂方法，则将忽略它们。如果发现了工厂方法和工厂构造函数，则将使用工厂方法代替构造函数。
    * Book将通过调用Book.fromTitle(String)factory方法并"42 Cats" 作为书名传递来创建自变量。
    * */
    @ParameterizedTest
    @ValueSource(strings = "42 Cats")
    void testWithImplicitFallbackArgumentConversion(Book book) {
        Assert.assertEquals("42 Cats", book.getTitle());
    }

    /*
    * 显式转换
    * 可以不使用隐式参数转换，而可以ArgumentConverter使用@ConvertWith注释（如以下示例）显式指定用于特定参数的。
    * 请注意，ArgumentConverter必须将的实现声明为顶级类或static嵌套类。
    * */
    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithExplicitArgumentConversion(
            @ConvertWith(ToStringArgumentConverter.class) String argument) {

        Assert.assertNotNull(ChronoUnit.valueOf(argument));
    }

    /*
    * 默认情况下，提供给方法的每个参数都@ParameterizedTest对应一个方法参数。
    * 因此，期望提供大量参数的参数源可能导致大量的方法签名。
    * 在这种情况下，ArgumentsAccessor可以使用an代替多个参数。
    * 使用此API，您可以通过传递给测试方法的单个参数来访问提供的参数。
    * 另外，如隐式转换中所述，支持类型 转换。
    * */

}
