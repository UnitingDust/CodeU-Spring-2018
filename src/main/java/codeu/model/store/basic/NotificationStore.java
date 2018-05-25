/*NotificationStore.java*/
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

package codeu.model.store.basic;

import codeu.controller.Notification;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class NotificationStore {

  /** Singleton instance of ProfileStore. */
  private static NotificationStore instance;

  /**
   * Returns the singleton instance of ProfileStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static NotificationStore getInstance() {
    if (instance == null) {
      instance = new NotificationStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static NotificationStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new NotificationStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Notifications from and saving Notifications to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Notification. */
  private List<Notification> notifications;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private NotificationStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    notifications = new ArrayList<>();
  }

  /** Load a set of randomly-generated Message objects. */
  public void loadTestData() {
    notifications.addAll(DefaultDataStore.getInstance().getAllNotifications());
  }

  /**
   * Access the Profile object with the given UUID.
   *
   * @return null if the UUID does not match any existing user associated with the notification.
   */
  public Notification getNotifications(UUID ID) {
    for (Notification notification : notifications) {
      if (notification.getUserID().toString().equals(ID.toString())) {
        return notification;
      }
    }
    return null;
  }

  /** Add a new profile to the current set of profiles known to the application. */
  public void addNotification(Notification notification) {
    notifications.add(notification);
   // persistentStorageAgent.writeThrough(notification);
  }

  /** Return true if the given profile is known to the application. */
  public boolean isUserRegistered(UUID ID) {
    for (Notification notification : notifications) {
    	if (notification.getUserID().toString().equals(ID.toString())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the List of Profiles stored by this ProfileStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setNotifications(List<Notification> notifications) {
    this.notifications = notifications;
  }
  
  /** Access the current set of notifications known to the application. */
  public List<Notification> getAllNotifications() {
    return notifications;
  }
}