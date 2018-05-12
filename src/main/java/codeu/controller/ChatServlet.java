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

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.HashMap; 
import java.util.ArrayList; 
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD
=======
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import  codeu.controller.StylizedTextParser;
>>>>>>> 224ed659d1609cbc37500138b00bedfd4ff2b5d8

import com.google.appengine.api.datastore.EntityNotFoundException;

/** Servlet class responsible for the chat page. */
public class ChatServlet extends HttpServlet {

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user navigates to the chat page. It gets the conversation title from
   * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
   * It then forwards to chat.jsp for rendering.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      System.out.println("Conversation was null: " + conversationTitle);
      response.sendRedirect("/conversations");
      return;
    }

    UUID conversationId = conversation.getId();

    List<Message> messages = messageStore.getMessagesInConversation(conversationId);

    HashMap<UUID, Boolean> allowedUsers = conversation.getAllowedUsers(); 
    List<String> allowedUsernames = new ArrayList<String>(); 
    if (conversation.getType().equals("private")) {
      for (UUID id : allowedUsers.keySet()) {
        allowedUsernames.add(userStore.getUser(id).getName()); 
      }
    }
    else
      allowedUsernames = null; 

    request.setAttribute("conversation", conversation);
    request.setAttribute("messages", messages);
    request.setAttribute("usernames", allowedUsernames); 
    request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the chat page. It gets the logged-in
   * username from the session, the conversation title from the URL, and the chat message from the
   * submitted form data. It creates a new Message from that data, adds it to the model, and then
   * redirects back to the chat page.
   */
   @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      response.sendRedirect("/conversations");
      return;
    }

    String messageContent = request.getParameter("message");

    // empty message, so post request came from Surprise submit button 
    if (messageContent == null) {
      if (!conversation.isAdmin(user.getId())) {
        // user is not an admin, so cannot add other users 
        request.setAttribute("error", "Cannot access admin privileges of this chat"); 
        //request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response); - TODO: forward request to show error message
        response.sendRedirect("/chat/" + conversationTitle);
        return; 
      }
      
      String surprisedUsername = request.getParameter("username");  
      if (surprisedUsername == null || surprisedUsername.length() == 0) {
        // nothing entered
        response.sendRedirect("/chat/" + conversationTitle); 
        return; 
      }

      User surprisedUser = userStore.getUser(surprisedUsername); 
      System.out.println(surprisedUser); 
      if (surprisedUser == null) {
        // user not found 
        response.sendRedirect("/chat/" + conversationTitle); 
        return; 
      }

      System.out.println("id " +  surprisedUser.getId()); 
      System.out.println(" user id " + user.getId()); 
      if (surprisedUser.getId().equals(user.getId())) {
        // user cannot add himself to the chat 
        response.sendRedirect("/chat/" + conversationTitle); 
        return; 
      }

      HashMap<UUID, Boolean> allowedUsers = conversation.getAllowedUsers(); 
      for (UUID id : allowedUsers.keySet()) {
        if (surprisedUser.getId().equals(id)) {
          // user cannot re-add a current member of that chat
          response.sendRedirect("/chat/" + conversationTitle); 
          return; 
        }
      }

      try {
        conversationStore.updateAllowedUsers(conversation, surprisedUser.getId());
      }
      catch (EntityNotFoundException e) {
        e.printStackTrace();
      }

      //surprisedUser.makeNotification("Surprise!", "Welcome to " + conversationTitle);  
      //request.setAttribute("message", "Surprise for "  + username + " scheduled for " + Calendar); 
      response.sendRedirect("/chat/" + conversationTitle);
    }
    
    //creates the new message
    Message message =
        new Message(
            UUID.randomUUID(),
            conversation.getId(),
            user.getId(),
            messageContent,
            Instant.now());

    messageStore.addMessage(message);

    // redirect to a GET request
    response.sendRedirect("/chat/" + conversationTitle);
  }
}