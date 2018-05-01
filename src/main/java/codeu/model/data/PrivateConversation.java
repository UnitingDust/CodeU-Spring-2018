/* PrivateConversation.java */
/* Supports functionality to create private conversations, add users, and schedule 
   surprises that correspond to adding another user in at a specified time */

package codeu.model.data;

import java.time.Instant; 
import java.util.UUID; 
import java.util.HashMap; 
import java.util.ArrayList;
import java.util.Timer; 
import java.util.TimerTask;  
import java.util.Date; 
import java.util.Calendar; 

/* Class representing a conversation, which can be thought of as a private chat room, created 
   by a User, contains allowed Users, messages, and  future users to be added */
public class PrivateConversation extends Conversation {

    /* private class for tasks that send a notification and adds user to this conversation at scheduled time */
    private class SurpriseTask extends TimerTask {
        /* ID of user to be surprised */
        private UUID id; 

        public SurpriseTask(UUID id) {
            this.id = id; 
        }

        public void run() {
            System.out.println("User" + id + " surprised!"); // change to send alert to notification class 
            existingUsers.put(id, true); 
            scheduledSurprises.remove(id); 
            timer.cancel(); 
        }
    }

    /// timer to keep track of TimerTasks
    private Timer timer; 

    // maps UUID to time user is scheduled to be surprised 
    public HashMap<UUID, SurpriseTask> scheduledSurprises;  

    // maps existing allowed users and whether or not they are admins 
    // default admin privileges set to true
    private HashMap<UUID, Boolean> existingUsers; 

    /* Constructs a new PrivateConversation with the owner as the only allowed user and no surprises */
    public PrivateConversation(UUID id, UUID owner, String title, Instant creation) {
        super(id, owner, title, creation); 
        timer = new Timer(); 
        scheduledSurprises = new HashMap<UUID, SurpriseTask>(); 
        existingUsers = new HashMap<UUID, Boolean>(); 
        existingUsers.put(owner, true); 
    }

    /* Returns true if user is allowed in private conversation */
    public boolean isAllowedUser(UUID id) {
        return existingUsers.containsKey(id); 
    }

    /* Returns true if  user is an admin */
    public boolean isAdmin(UUID id) {
        return existingUsers.get(id); 
    }

    /* schedules a surprise at specified time for user id to be added into this conversation */
    public void scheduleSurprise(UUID id, Date time) {
        SurpriseTask surprise = new SurpriseTask(id);  
        timer.schedule(surprise, time); 
        scheduledSurprises.put(id, surprise); 
    }

    /* checks surprise time of user id */
    public Calendar checkSurpriseTime(UUID id) {
        long time = scheduledSurprises.get(id).scheduledExecutionTime(); 
        Calendar cal = Calendar.getInstance(); 
        cal.setTimeInMillis(time); 
        return cal; 
    }

    /* adds user id to existing users with admin privileges */
    public void addUser(UUID id) {
        existingUsers.put(id, true); 
    }

    /* checks if any surprises should have happened but have not yet, and returns false if so, may be useful for debugging */
    public boolean checkMissedSurprises() {
        Calendar currentTime = Calendar.getInstance(); 
    
        for (UUID id : this.scheduledSurprises.keySet()) {
            Calendar surpriseTime = checkSurpriseTime(id);      
                /* check all scheduled surprises */
             if (surpriseTime.before(currentTime)) {
                return true; 
            }
        }
        return false; // found no missed surprises 
    }
}