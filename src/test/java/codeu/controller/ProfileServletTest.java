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

package codeu.controller;

import codeu.model.data.Profile;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ProfileStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class ProfileServletTest {
  private ProfileServlet profileServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;
  private ProfileStore mockProfileStore;
  private MessageStore mockMessageStore;

  @Before
  public void setup() {
    profileServlet = new ProfileServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockProfileStore = Mockito.mock(ProfileStore.class);
    profileServlet.setProfileStore(mockProfileStore);

    mockMessageStore = Mockito.mock(MessageStore.class);
    profileServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    profileServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGetValidProfile() throws IOException, ServletException {
    List<Message> fakeMessageList = new ArrayList<>();
    fakeMessageList.add(
        new Message(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "test_message",  Instant.now()));
    
    User user = new User(UUID.randomUUID(), "test_username", "test_password", Instant.now());
    Profile profile = new Profile(user.getId(), "test_description");
    
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/profile/test_username"); 
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    Mockito.when(mockProfileStore.getProfile(user.getId())).thenReturn(profile);
    Mockito.when(mockMessageStore.getMessagesByUser(user.getId())).thenReturn(fakeMessageList);
    
    profileServlet.doGet(mockRequest, mockResponse);
    
    // Check to make sure that all the attributes are properly set 
    Mockito.verify(mockRequest).setAttribute("username", "test_username");
    Mockito.verify(mockRequest).setAttribute("messages", fakeMessageList);
    Mockito.verify(mockRequest).setAttribute("description", "test_description");

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
    // Provide a invalid profile link
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/profile/invalid_username"); 
    
    profileServlet.doGet(mockRequest, mockResponse);

    // Verify that the error and username attribute was set
    Mockito.verify(mockRequest).setAttribute("username", "invalid_username");
    Mockito.verify(mockRequest).setAttribute("error", "Invalid User");
    
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_LoggedInUser() throws IOException, ServletException, EntityNotFoundException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/profile/test_username");  
    Mockito.when(mockSession.getAttribute("user")).thenReturn("not_null");
    Mockito.when(mockRequest.getParameter("editDescription")).thenReturn("new_description");

    UUID ID = UUID.randomUUID();
    User fakeUser = new User(ID, "test_username", "test_password", Instant.now());
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);
    
    Profile fakeProfile = new Profile(ID, "test_description");
    mockProfileStore.addProfile(fakeProfile);
    Mockito.when(mockProfileStore.getProfile(ID)).thenReturn(fakeProfile);

    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockProfileStore).updateProfile(fakeProfile, "new_description");
    Mockito.verify(mockResponse).sendRedirect("/profile/test_username");
  }
  
  @Test
  public void testDoPost_NotLoggedIn() throws IOException, ServletException, EntityNotFoundException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/profile/test_username");  
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);
    Mockito.when(mockRequest.getParameter("editDescription")).thenReturn("new_description");

    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockResponse).sendRedirect("/profile/test_username");
  }

  @Test
  public void notification(){
   User testUser = new User(UUID.randomUUID(), "test_username", "test_password", Instant.now());
   testUser.setNotificationTrue();
   Notification testN =  testUser.makeNotification("testTitle", "testMessage");
   System.out.println("this is the notif titile: " + testN.getTitle());
   System.out.println("this is the notif message: " + testN.getMessage() );
   System.out.println("this is the notif user: " + testN.getUser().getName() );
   Assert.assertEquals("test_username",testN.getUser().getName());
   Assert.assertEquals("testTitle",testN.getTitle());
   Assert.assertEquals("testMessage",testN.getMessage());
 }
 
}