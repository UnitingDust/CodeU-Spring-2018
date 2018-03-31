package codeu.model.data;

import java.util.UUID;

/** Class representing a user's profile */
public class Profile {
	private final UUID user;
	private String description;
	
	/**
	 * Constructs a new Profile
	 * 
	 * @param author ID of the user's profile
	 * @param description Description of user
	 */
	public Profile(UUID user, String description)
	{
		this.user = user;
		this.description = description;
	}
	
	/** Return the ID of the user of the profile */
	public UUID getUserID()
	{
		return this.user;
	}
	
	/** Return the profile description */
	public String getDescription()
	{
		return this.description;
	}
	
	/** Set the profile description */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
