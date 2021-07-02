package entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Log
//@JsonPropertyOrder用于类， 和 @JsonProperty 的index属性类似，指定属性在序列化时 json 中的顺序
@JsonPropertyOrder({"name","identity"})
// @JsonIgnoreProperties批量忽略属性，不进行序列化。该注解是类注解。该注解在Java类和JSON不完全匹配的时候使用。
@JsonIgnoreProperties(value = {"id"})
/*用于序列化与反序列化时的驼峰命名与小写字母命名转换
 **序列化的时候该注解可将驼峰命名的字段名转换为下划线分隔的小写字母命名方式。
 **反序列化的时候可以将下划线分隔的小写字母转换为驼峰命名的字段名
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {
    //可用于字段、getter/setter、构造函数参数上，作用相同，都会对相应的字段产生影响。使相应字段不参与序列化和反序列化。
    @JsonIgnore
    private Map<String,Object> other = new HashMap<>();
    private String id;
    private String username;
    private String identity;
    private String sex;
    // @JsonProperty用于属性，把属性的名称序列化时转换为另外一个名称。
    @JsonProperty("create_date")
    private Date createDate;
    // 空对象
    private Integer age;
    @JsonFormat(timezone = "GMT+9",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    public User(String username,String gender,Date createDate) {
        this.username = username;
        this.sex = gender;
        this.createDate = createDate;
    }
    //用于构造方法，和 @JsonProperty 配合使用，适用有参数的构造方法。
    @JsonCreator
    public User(@JsonProperty("user_name") String userName){
        System.out.println("@JsonCreator 注解使得反序列化自动执行该构造方法 " + userName);
        // 反序列化需要手动赋值
        this.username = userName;
    }

    public User(String string, String string1, Object o, Class<Date> dateClass) {
    }

    //用于属性或者方法，设置未反序列化的属性名和值作为键值存储到 map 中
    @JsonAnySetter
    public void set(String key,Object value){
        other.put(key, value);
    }

    //用于方法 ，获取所有未序列化的属性
    @JsonAnyGetter
    public Map<String,Object> any(){
        return other;
    }

    /*
     * 类注解。需开启mapper.enable(SerializationFeature.WRAP_ROOT_VALUE)，
     * 用于序列化时输出带有根属性名称的 JSON 串，
     * 形式如 {"root_name":{"id":1,"name":"zhangsan"}}。
     * 但不支持该 JSON 串反序列化。
     * */
    //@JsonRootName

    public static Module JavaTimeModule(){
        JavaTimeModule module = new JavaTimeModule();
        String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        String DATE_FORMAT = "yyyy-MM-dd";
        String TIME_FORMAT = "HH:mm:ss";
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern((TIME_FORMAT))));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
        return module;
    }
}
