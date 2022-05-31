import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class FieldOrderTest2 {
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
        School(String name){this.name=name;}
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new Person("matteo",new School("Fermi")),"{\"name\":\"matteo\",\"school\":{\"name\":\"Fermi\"}}"},
                {new Person("mirko",new School("Amaldi")),"{\"name\":\"mirko\",\"school\":{\"name\":\"Amaldi\"}}"},
                {new Person("njb",new School("llyz")),"{\"name\":\"njb\",\"school\":{\"name\":\"llyz\"}}"}
        });
    }
    private final Person p;
    private final String Expected;
    public FieldOrderTest2(Person p, String Expected){
        this.p = p;
        this.Expected=Expected;
    }
    @Test
    public void test_field_order() throws Exception {
        String json = JSON.toJSONString(p);
        assertEquals(Expected, json);
    }
}
