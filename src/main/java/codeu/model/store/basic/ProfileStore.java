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

import codeu.model.data.Profile;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class ProfileStore {

  /** Singleton instance of ProfileStore. */
  private static ProfileStore instance;

  /**
   * Returns the singleton instance of ProfileStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static ProfileStore getInstance() {
    if (instance == null) {
      instance = new ProfileStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static ProfileStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new ProfileStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Profiles from and saving Profiles to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Profiles. */
  private List<Profile> profiles;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private ProfileStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    profiles = new ArrayList<>();
  }

  /** Load a set of randomly-generated Message objects. */
  public void loadTestData() {
    profiles.addAll(DefaultDataStore.getInstance().getAllProfiles());
  }

  /**
   * Access the Profile object with the given UUID.
   *
   * @return null if the UUID does not match any existing Profile.
   */
  public Profile getProfile(UUID ID) {
    for (Profile profile : profiles) {
      if (profile.getUserID().toString().equals(ID.toString())) {
        return profile;
      }
    }
    return null;
  }

  /** Add a new profile to the current set of profiles known to the application. */
  public void addProfile(Profile profile) {
    profiles.add(profile);
    persistentStorageAgent.writeThrough(profile);
  }

  /** Return true if the given profile is known to the application. */
  public boolean isUserRegistered(UUID ID) {
    for (Profile profile : profiles) {
    	if (profile.getUserID().toString().equals(ID.toString())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the List of Profiles stored by this ProfileStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setProfiles(List<Profile> profiles) {
    this.profiles = profiles;
  }
  
  /**
   * Updates the Profile description locally and on the Google Database
   */
  public void updateProfile(Profile profile, String description)
  {
	 profile.setDescription(description);
	 persistentStorageAgent.writeThrough(profile);
  }
}