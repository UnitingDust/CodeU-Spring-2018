// StylizedTextParserTest.java 

package codeu.controller;

import org.junit.Assert; 
import org.junit.Before; 
import org.junit.Test;
import codeu.controller.StylizedTextParser; 

public class StylizedTextParserTest {
    
    private StylizedTextParser testParser; 
    @Before
    public void setup() {
        testParser = new StylizedTextParser(); 
    }

    @Test 
    public void testSimpleParsing() {
        Assert.assertEquals("<b>bold this</b>", testParser.parse("*bold this*")); 
        Assert.assertEquals("<i>italicize this</i>", testParser.parse("_italicize this_")); 
        Assert.assertEquals("<code>monospace this</code>", testParser.parse("'monospace this'")); 
        Assert.assertEquals("<s>strikethrough this</s>", testParser.parse("~strikethrough this~")); 
        Assert.assertEquals("<u>underline this</u>", testParser.parse("-underline this-")); 
    }

    @Test
    public void testNoParsing() {
        Assert.assertEquals("do not bold", testParser.parse("do *not bold")); 
        Assert.assertEquals("do not bold", testParser.parse("do not bold*")); 
    }
}