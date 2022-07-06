import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FieldOrder3Test {
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
    private void configure1(String name,String schoolName){
        School s=new School(schoolName);
        this.p = new Person(name,s);
        this.feature=SerializerFeature.PrettyFormat;
        this.defaultfeature=0;
        this.Expected="{\n\tname:\""+name+"\",\n\tschool:{\n\t\tname:\""+schoolName+"\"\n\t}\n}";
    }
    private void configure2(String name,String schoolName){
        School s=new School(schoolName);
        this.p = new Person(name,s);
        this.feature=null;
        this.defaultfeature=1;
        this.Expected="{\"name\":\""+name+"\",\"school\":{\"name\":\""+schoolName+"\"}}";

    }
    public void configure(String name,String schoolName){
        School s=new School(schoolName);
        this.p = new Person(name,s);
        this.Expected="{\"name\":\""+name+"\",\"school\":{\"name\":\""+schoolName+"\"}}";
    }
    int test;
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {0,"njb","llyz"},//test loro
                {1,"ciao","ciao1"}, //nuovo Test
                {2,"ciao","ciao1",} //

        });
    }

    private int defaultfeature;
    private SerializerFeature feature;

    public FieldOrder3Test(int i,String name, String schoolName){
        switch (i) {
            case 0:
                this.test=0;
                configure(name, schoolName);
                break;
            case 1:
                this.test=1;
                configure1(name,schoolName);
                break;
            case 2:
                this.test=2;
                configure2(name,schoolName);
                break;
        }
    }
    @Test
    public void test_field_order() throws Exception {
        if(this.test==0){
            String json = JSON.toJSONString(this.p, SerializeConfig.globalInstance, new SerializeFilter[0], (String) null, JSON.DEFAULT_GENERATE_FEATURE);
            assertEquals(Expected, json);
        }
        else {
            Assert.assertTrue(true);
        }
    }
    @Test
    public void test_field_order1() throws Exception {
        if(this.test==1){
            String json = JSON.toJSONString(this.p, this.defaultfeature,this.feature);
            assertEquals(Expected, json);
        }
        else {
            Assert.assertTrue(true);
        }
    }
    @Test
    public void test_field_order2() throws Exception {
        if(this.test==2){
            String json = JSON.toJSONString(this.p,this.defaultfeature);
            assertEquals(Expected, json);
        }
        else {
            Assert.assertTrue(true);
        }
    }
}
