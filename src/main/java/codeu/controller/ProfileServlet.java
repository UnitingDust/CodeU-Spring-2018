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
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the profile page. */
public class ProfileServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Profiles. */
  private ProfileStore profileStore;

  /** Store class that gives access to Profiles. */
  private MessageStore messageStore;

  /**
   * Set up state for handling conversation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setProfileStore(ProfileStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ProfileStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setProfileStore(ProfileStore profileStore) {
    this.profileStore = profileStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * This function fires when a user navigates to the profile page. It gets all of the
   * profiles and messages from the model and forwards to profiles.jsp for rendering the list.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
	  
	// Parse username from URL link
    String requestUrl = request.getRequestURI();
    String username = requestUrl.substring("/profile/".length());
    
    User user = userStore.getUser(username);
    request.setAttribute("username", username);
    
    if (user == null)
    {
    	request.setAttribute("error", "Invalid User");
    }
    
    else
    {
    	UUID ID = userStore.getUser(username).getId();
    	List<Message> messages = messageStore.getMessagesByUser(ID);
    	request.setAttribute("messages", messages);
    	
    	Profile profile = profileStore.getProfile(ID);
    	request.setAttribute("description", profile.getDescription());
    }
    
    request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  }

  /**
   * This function fires when a user edits the description on the profile page. 
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

	// Parse username from URL link
	String requestUrl = request.getRequestURI();
	String username = requestUrl.substring("/profile/".length());
	
	// TODO
	// Error checking when user isn't logged in anymore
	
	UUID ID = userStore.getUser(username).getId();
	
    String description = request.getParameter("editDescription");
    profileStore.updateProfile(profileStore.getProfile(ID), description);

    response.sendRedirect("/profile/" + username);
  }
}

