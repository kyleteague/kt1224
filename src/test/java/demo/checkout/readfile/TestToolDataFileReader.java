package demo.checkout.readfile;

import org.junit.Test;

import demo.checkout.tool.Tool;
import junit.framework.Assert;

public class TestToolDataFileReader {

	@Test
	public void testGetToolWithCode() {
		
		ToolDataFileReader reader = new ToolDataFileReader("src/test/resources/test_tools.data");
		
		Tool tool = reader.getToolWithCode("LADW");
		Assert.assertEquals("LADW", tool.getCode());
		Assert.assertEquals("Ladder", tool.getType());
		Assert.assertEquals("Werner", tool.getBrand());
		
		tool = reader.getToolWithCode("JAKD");
		Assert.assertEquals("JAKD", tool.getCode());
		Assert.assertEquals("Jackhammer", tool.getType());
		Assert.assertEquals("DeWalt", tool.getBrand());
		
		tool = reader.getToolWithCode("JAKR");
		Assert.assertEquals("JAKR", tool.getCode());
		Assert.assertEquals("Jackhammer", tool.getType());
		Assert.assertEquals("Ridgid", tool.getBrand());
		
		tool = reader.getToolWithCode("CHNS");
		Assert.assertEquals("CHNS", tool.getCode());
		Assert.assertEquals("Chainsaw", tool.getType());
		Assert.assertEquals("Stihl", tool.getBrand());
		
	}
}
