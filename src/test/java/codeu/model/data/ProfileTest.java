package codeu.model.data;

import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ProfileTest {
	
	@Test
	public void testCreate() {
		String description = "test";
		String description2 = "test2";
		UUID userID = UUID.randomUUID();
		Profile profile = new Profile(userID, description);

	    Assert.assertEquals(userID, profile.getUserID());
	    Assert.assertEquals(description, profile.getDescription());
	    
	    profile.setDescription(description2);
	    Assert.assertEquals(description2, profile.getDescription());
	  }

}
