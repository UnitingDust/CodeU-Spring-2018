/*Notification.java*/
/*pop up notification when user is added to a group chat or an event is taking place*/
package codeu.controller;
import codeu.model.data.User;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import codeu.model.data.Conversation;

public class Notification{

	//the user who is receiving the notification
	private User userNotified;

//the group chat that added the user and is sending the notification
	private Conversation senderGC;

//Title of the notification
	private String notificationTitle;

//message of the notification
	private String notificationMsg;

	//Constructor
	public Notification(User user, Conversation grChat, String title, String message){

		userNotified = user;
		senderGC = grChat;
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

	public UUID getUserID(){
		return userNotified.getId();
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

	//returns the title of the message
	public Conversation getGroupChat(){
		return senderGC;
	}
}