package codeu.model.store.basic;

import codeu.model.data.Profile;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
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
      new Profile(UUID.randomUUID(), "test_profile_one", Instant.ofEpochMilli(1000));
  private final Profile PROFILE_TWO =
      new Profile(UUID.randomUUID(), "test_profile_two", Instant.ofEpochMilli(2000));
  private final Profile PROFILE_THREE =
      new Profile(UUID.randomUUID(), "test_profile_three", Instant.ofEpochMilli(3000));

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
  public void testProfile_byId_found() {
    User resultProfile = profileStore.getProfile(PROFILE_ONE.getId());

    assertEquals(PROFILE_ONE, resultProfile);
  }

  @Test
  public void testProfile_byAuthor_found() {
    User resultProfile = profileStore.getProfileAuthor(PROFILE_ONE.getAuthor());

    assertEquals(PROFILE_ONE, resultProfile);
  }

  @Test
  public void testGetProfile_byAuthor_notFound() {
    User resultProfile = profileStore.getProfile(UUID.randomUUID());

    Assert.assertNull(resultProfile);
  }

  @Test
  public void testGetProfile_byId_notFound() {
    User resultProfile = profileStore.getProfile(UUID.randomUUID());

    Assert.assertNull(resultProfile);
  }

  @Test
  public void testAddProfile() {
    Profile inputProfile = new Profile(UUID.randomUUID(), "description");

    profileStore.addProfile(inputProfile);
    Profile resultProfile = profileStore.getProfile("description");

    assertEquals(inputProfile, resultProfile);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputProfile);
  }

  @Test
  public void testIsUserRegistered_true() {
    Assert.assertTrue(profileStore.isUserRegistered(PROFILE_ONE.getAuthor()));
  }

  @Test
  public void testIsUserRegistered_false() {
    Assert.assertFalse(profileStore.isUserRegistered("fake author"));
  }

  private void assertEquals(Profile expectedProfile, Profile actualProfile) {
    Assert.assertEquals(expectedProfile.getUserID(), actualProfile.getUserID());
    Assert.assertEquals(expectedProfile.getDescription(), actualProfile.getDescription());
    
  }
}