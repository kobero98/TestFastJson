import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParserConfigTest2 extends TestCase {

    private final String param1;
    private final int expected;
    public ParserConfigTest2(String param1,int expected){
        this.param1=param1;
        this.expected=expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"{\"value\":123}",123}, //StringadiInput,Valore Atteso
                {"{\"value\":0}",0},

        });
    }
    @Test
    public void test_0() throws Exception {
        ParserConfig config = new ParserConfig();
        config.getDeserializers();
    }

    @Test
    public void test_1() throws Exception {
        ParserConfig config = new ParserConfig(Thread.currentThread().getContextClassLoader());
        Model model = JSON.parseObject(this.param1, Model.class, config);
        Assert.assertEquals(this.expected, model.value);
    }
    public static class Model {
        public int value;
    }
}
