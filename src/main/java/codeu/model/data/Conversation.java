// Copyright 2017 Google Inc.

//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant; 
import java.util.UUID; 
import java.util.HashMap; 
import java.util.ArrayList;
import java.util.Timer; 
import java.util.TimerTask;  
import java.util.Date; 
import java.util.Calendar; 

/**
 * Class representing a conversation, which can be thought of as a chat room. Conversations are
 * created by a User and contain Messages.
 */
public class Conversation {
  public final UUID id;
  public final UUID owner;
  public final Instant creation;
  public final String title;
  public final String type; 

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

  // maps existing allowed users and whether or not they are admins 
  // default admin privileges set to true
  // null if type == "public"
  private HashMap<UUID, Boolean> existingUsers; 

  // timer to keep track of TimerTasks
  private Timer timer; 

  // maps UUID to time user is scheduled to be surprised 
  public HashMap<UUID, SurpriseTask> scheduledSurprises; 

  /**
   * Constructs a new Conversation.
   *
   * @param id the ID of this Conversation
   * @param owner the ID of the User who created this Conversation
   * @param title the title of this Conversation
   * @param creation the creation time of this Conversation
   * @param type the type (public/private) of this Conversation
   */
  public Conversation(UUID id, UUID owner, String title, Instant creation, String type) {
    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
    this.type = type; 

    if (type != null && type.equals("private")) {
      timer = new Timer(); 
      scheduledSurprises = new HashMap<UUID, SurpriseTask>(); 
      existingUsers = new HashMap<UUID, Boolean>(); 
      existingUsers.put(owner, true); 
    }
    else {
      existingUsers = null; 
      timer = null; 
      scheduledSurprises = null; 
    }
  }

  // if no type is provided, default to public Conversation 
  public Conversation(UUID id, UUID owner, String title, Instant creation) {
    this(id, owner, title, creation, "public"); 
  }

  /** Returns the ID of this Conversation. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the User who created this Conversation. */
  public UUID getOwnerId() {
    return owner;
  }

  /** Returns the title of this Conversation. */
  public String getTitle() {
    return title;
  }

  /** Returns the creation time of this Conversation. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns the type of this Conversation. */
  public String getType() {
    if (type == null) 
      return "public"; 
    return type;
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
