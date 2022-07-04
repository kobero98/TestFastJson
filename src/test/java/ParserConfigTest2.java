import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParserConfigTest2 extends TestCase {

    private ParserConfig config0;
    private ParserConfig config1;
    private Type classType;
    private  String param1;
    private  int expected;
    public static class Model {
        public int value;
    }
    public ParserConfigTest2(String param1,int expected){
        configure(param1,expected);
    }

    private void configure(String param1, int expected) {
        this.config0 =new ParserConfig();
        this.param1=param1;
        this.expected=expected;
        this.config1 = new ParserConfig(Thread.currentThread().getContextClassLoader());
        this.classType=Model.class;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"{\"value\":123}",123}, //StringadiInput,Valore Atteso
        });
    }
    @Test
    public void test_0() throws Exception {
        config0.getDeserializers();
    }

    @Test
    public void test_1() throws Exception {

        Model model = JSON.parseObject(this.param1, this.classType,this.config1);
        Assert.assertEquals(this.expected, model.value);
    }

}
