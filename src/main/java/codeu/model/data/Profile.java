package codeu.model.data;

import java.util.UUID;

/** Class representing a user's profile */
public class Profile {
	private final UUID author;
	private String description;
	
	/**
	 * Constructs a new Profile
	 * 
	 * @param author ID of the user's profile
	 * @param description Description of user
	 */
	public Profile(UUID author, String description)
	{
		this.author = author;
		this.description = description;
	}
	
	/** Return the ID of the author of the profile */
	public UUID getAuthor()
	{
		return this.author;
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
