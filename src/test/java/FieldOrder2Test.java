import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FieldOrder2Test {
    static class Person{
    public Person(String name,School school) {
        this.name=name;
        this.school=school;
    }
    private String name;
    private School school;
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
    static class School{
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public School(String name){this.name=name;}
    }
    private  Person p;
    private  String Expected;
    public void configure(String name,String schoolName){
        School s=new School(schoolName);
        this.p = new Person(name,s);
        this.Expected="{\"name\":\""+name+"\",\"school\":{\"name\":\""+schoolName+"\"}}";
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            //    {"njb","llyz"},
        });
    }

    public FieldOrder2Test(String name, String schoolName){
        configure(name,schoolName);
    }
    @Test
    public void test_field_order() throws Exception {
        String json = JSON.toJSONString(p);
        assertEquals(Expected, json);
    }
}
