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

public class ProfileServletTest {

  private ConversationServlet conversationServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private UserStore mockUserStore;

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
    ProfileServlet.setProfileStore(mockProfileStore);

    mockMessageStore = Mockito.mock(MessageStore.class);
    MessageServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    conversationServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    List<Message> fakeMessageList = new ArrayList<>();
    fakeMessageList.add(
        new Message(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "test_message",  Instant.now()));
    Mockito.when(mockMessageStore.getAllMessages()).thenReturn(fakeMessageList);
    String fakeUsername = requestUrl.substring("/profile/".length());
    UUID fakeID = userStore.getUser(username).getId()
    Profile fakeProfile = profileStore.getProfile(id);

    profileServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("profile", "messages", fakeProfile, fakeMessageList);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockProfileStore, Mockito.never())
        .updateProfile(Mockito.any(Profile.class));
    Mockito.verify(mockResponse).sendRedirect("/profile");
  }

  @Test
  public void testDoPost_NewDescription() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("description")).thenReturn("test_description");
    Mockito.when(mockSession.getAttribute("profile")).thenReturn("test_profile");

    Profile fakeProfile = new Profile(UUID.randomUUID(), "test_description");
    Mockito.when(mockUserStore.getProfile("test_description")).thenReturn(fakeProfile);


    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockResponse).sendRedirect("/profile/test_profile");
  }
}
