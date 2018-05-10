/*Notification.java*/
/*pop up notification when user is added to a group chat or an event is taking place*/
package codeu.controller;
import codeu.model.data.User;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Notification{

// Fields


	//the user who is receiving the notification
	private User userNotified;

//the group chat that added the user and is sending the notification
	//private GroupChat senderGC;

//Title of the notification
	private String notificationTitle;

//message of the notification
	private String notificationMsg;


	

	// Methods
	
	//Constructor
	public Notification(User user, String title, String message){

		userNotified = user;
		//senderGC = grChat;
		notificationTitle = title;
		notificationMsg = message;

	}

	//returns the title of the message
	public String getTitle(){
		return notificationTitle;
	}

	//sets the title of the message
	public void setTitle(String newTitle){
		notificationTitle = newTitle;
	}

	//returns the User
	public User getUser(){
		return userNotified;
	}

	//sets the user
	public void setUser(User newUser){
		userNotified = newUser;
	}

	//returns the message of the notification
	public String getMessage(){
		return notificationMsg;
	}

	//sets the message of the notification
	public void setMessage(String msg){
		notificationMsg = msg;
		
	}

public static void main(String[] args){
    User testUser = new User(UUID.randomUUID(), "test_username", "test_password", Instant.now());
    testUser.setNotificationTrue();
   Notification testN =  testUser.makeNotification("hi", "hello");
   System.out.println("this is the notif titile: " + testN.getTitle());
    System.out.println("this is the notif message: " + testN.getMessage() );
     System.out.println("this is the notif user: " + testN.getUser().getName() );
}


}