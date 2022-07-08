
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;

import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class ParserConfig3Test extends TestCase {


    private String inputString;
    private Type classType;
    private ParserConfig config0;
    private ParserConfig config1;
    private ParseProcess processor;
    private int featureValues;
    private Feature features;

    // Constructor
    public ParserConfig3Test(String inputString, boolean isProcessor, int featureValues, boolean isFeature) {
        configure(inputString, isProcessor, featureValues, isFeature);
    }

    private void configure(String inputString, boolean isProcessor, int featureValues, boolean isFeature) {
        this.inputString = inputString;
        this.featureValues = featureValues;

        this.config0 = new ParserConfig();
        this.config1 = new ParserConfig(Thread.currentThread().getContextClassLoader());

        if(isProcessor) this.processor = this.new MyExtraProcessor();
        else this.processor = null;

        if(isFeature) this.features = Feature.AllowArbitraryCommas;
        else this.features = null;

        this.classType = Model.class;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"{\"value\":123}", false, 14, false},
                {"{\"value\":123}", true, 14, true}
                // inputString	   proc	 val, feat
        });
    }

    @Test
    public void test_0() throws Exception {
        this.config0.getDeserializers();
    }

    @Test
    public void test_1() throws Exception {
        JSONObject jo = new JSONObject(this.inputString);
        int expected = jo.getInt("value");

        Model model;
        if(this.features != null)
            model = JSON.parseObject(this.inputString, this.classType, this.config1, this.processor, this.featureValues, this.features);
        else
            model = JSON.parseObject(this.inputString, this.classType, this.config1, this.processor, this.featureValues);

        Assert.assertEquals(expected, model.value);
    }

    public static class Model {
        public int value;
    }

    public class MyExtraProcessor implements ExtraProcessor, ExtraTypeProvider {
        public void processExtra(Object object, String key, Object value) {
            VO vo = (VO) object;
            vo.getAttributes().put(key, value);
        }

        public Type getExtraType(Object object, String key) {
            if ("value".equals(key)) {
                return int.class;
            }
            return null;
        }
    }

    public static class VO {

        private int id;
        private Map<String, Object> attributes = new HashMap<String, Object>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

    }

}
