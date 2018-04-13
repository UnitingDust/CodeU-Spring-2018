package codeu.model.store.basic;

import codeu.model.data.Profile;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito; 

public class ProfileStoreTest {

  private ProfileStore profileStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final Profile PROFILE_ONE =
      new Profile(UUID.randomUUID(), "test_profile_one");
  private final Profile PROFILE_TWO =
      new Profile(UUID.randomUUID(), "test_profile_two");
  private final Profile PROFILE_THREE =
      new Profile(UUID.randomUUID(), "test_profile_three");

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    profileStore = ProfileStore.getTestInstance(mockPersistentStorageAgent);

    final List<Profile> profileList = new ArrayList<>();
    profileList.add(PROFILE_ONE);
    profileList.add(PROFILE_TWO);
    profileList.add(PROFILE_THREE);
    profileStore.setProfiles(profileList);
  }

  @Test
  public void testgetProfile() {
    Profile resultProfile = profileStore.getProfile(PROFILE_ONE.getUserID());
    Profile nullProfile = profileStore.getProfile(UUID.randomUUID());
    
    assertEquals(PROFILE_ONE, resultProfile);
    Assert.assertEquals(null, nullProfile);  
  }

  @Test
  public void testAddProfile() {
	UUID id = UUID.randomUUID();
    Profile inputProfile = new Profile(id, "test_description");

    profileStore.addProfile(inputProfile);
    Profile resultProfile = profileStore.getProfile(id);

    assertEquals(inputProfile, resultProfile);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputProfile);
  }

  @Test
  public void testIsUserRegistered_true() {
    Assert.assertTrue(profileStore.isUserRegistered(PROFILE_ONE.getUserID()));
  }

  @Test
  public void testIsUserRegistered_false() {
    Assert.assertFalse(profileStore.isUserRegistered(UUID.randomUUID()));
  }

  private void assertEquals(Profile expectedProfile, Profile actualProfile) {
    Assert.assertEquals(expectedProfile.getUserID(), actualProfile.getUserID());
    Assert.assertEquals(expectedProfile.getDescription(), actualProfile.getDescription());
    
  }
}