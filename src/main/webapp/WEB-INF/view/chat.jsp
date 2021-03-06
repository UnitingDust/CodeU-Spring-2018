<%--
  Copyright 2017 Google Inc.
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  --%>
  <%@ page import="java.util.ArrayList" %>
  <%@ page import="java.util.List" %>
  <%@ page import="java.util.HashMap" %>
  <%@ page import="java.util.UUID" %>
  <%@ page import="codeu.model.data.Conversation" %>
  <%@ page import="codeu.model.data.Message" %>
  <%@ page import="codeu.model.store.basic.UserStore" %>
  <%@ page import="codeu.controller.StylizedTextParser" %>
  <%
  Conversation conversation = (Conversation) request.getAttribute("conversation");
  List<Message> messages = (List<Message>) request.getAttribute("messages");
  HashMap<UUID, Boolean> allowedUsers = conversation.getAllowedUsers(); 
  List<String> usernames = new ArrayList<String>(); 
  StylizedTextParser messageParser = new StylizedTextParser(); 
  %>

  <!DOCTYPE html>
  <html>
  <head>
    <title><%= conversation.getTitle() %></title>
    <link href="https://fonts.googleapis.com/css?family=Oxygen|Pacifico" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
    <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">

  <nav>
    <% if(request.getSession().getAttribute("user") != null){ %>
    <a id="navTitle" href="/conversations">Gossip Guru</a>   
    <% } else{ %>
    <a id="navTitle" href="/">Gossip Guru</a>
    <% } %>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
    <a href="/login">Login</a>
    <a href="/register">Register</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
    <a href="/profile/<%= request.getSession().getAttribute("user") %>">Profile</a>
    <a href="/logout.jsp">Logout</a>
    <% } %>
  </nav>

  <div id="container">
   <% if(request.getAttribute("error") != null){ %>

   <h2 style="color:red"><%= request.getAttribute("error") %></h2>

   <% } else { %>

   <h1><%= conversation.getTitle() %>
    <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <h1><strong>Gossip Unloading...</strong></h1>
    <img " src="http://ichef.bbci.co.uk/corporate2/images/width/live/p0/53/d3/p053d3rz.jpg/624" width="750" height="150"> 
    <h2><strong> Add Some Jazz to Your Gossip</strong></h2>
    <ul>  
      <li><i>Italics:  </i>  _italics_</li> 
      <li><b>Bold:  </b> *Bold*</li> 
      <li><strike>Strikethrough:  </strike> ~strikethrough~</li>
      <li><u>Underline:  </u> -underline-</li>
      <li><code>Code:  </code> &#39;code&#39;</li> 
    </ul>
    
    <% if (conversation.time != null) { %>
    	<h2 style="color:pink">Current Set Time: <%= conversation.time %></h2>
    <% } %>
    
    <% if (conversation.date != null) { %>
    	<h2 style="color:pink">Current Set Date: <%= conversation.date %></h2>
    <% } %>
    
    <div style="padding: 10px" id="chat">
      <ul>
        <%
        for (Message message : messages) {
        	String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
        	
        	if (message.getContent() == null)
            	continue; // do not display empty messages for now
            	
        	String parsedContent = messageParser.parse(message.getContent());
        %>
            <li><strong><%= author %>:</strong> <%= parsedContent %></li>
            <%
          }
          %>
        </ul>
      </div>
      <hr/>
      
      <% if (request.getSession().getAttribute("user") != null) { %>
      <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input id="input-text-long" type="text" name="message">
        <button id="submit-button" type="submit">Send</button>

      </form>
      <% if (conversation.getType().equals("private")) { %>
      <%for (UUID id : allowedUsers.keySet())
      usernames.add(UserStore.getInstance().getUser(id).getName());%>
      <hr/>
      <h2 style="color:purple; font-weight: bold">Plan a Surprise</h2>
      <p><i>Surprise a friend by adding them to this conversation to join the fun!</i></p>
      
      <% if (request.getAttribute("invalid") != null) { %>
      <h2 style="color:red"><%= request.getAttribute("invalid") %></h2>
      <% } %>
      
      <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <div class="form-group">
          <label class="form-control-label">Username:</label>
          <input id="input-text" type="text" name="username">
        </div>
        <button id="submit-button" type="submit">Schedule</button>
      </form>
      <hr/>

      <h2 style="color:purple; font-weight: bold">Schedule a Date</h2>
<% if (conversation.getType().equals("private")) { %>
    <h3>Enter the Event Surprise Date</h3>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input id="date" type="date" name="date">
        <br/>
        <button type="submit" name="action" value="two">Submit</button>
    </form>

      </form>
    
    <hr/>
    
    <h2 style="color:purple; font-weight: bold">Schedule a Time</h2>
    <h3>Enter the Event Surprise Time</h3>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input id="event-time" type="time" name="time">
        <br/>
        <button type="submit" name="action" value="three">Submit</button>
    </form>
<%} %>
<hr/>

      <% if (usernames != null) { %>
      <h2 style="font-weight: bold">Chat Members</h2>
      <ul>
        <% for (String username : usernames) { %>
        <li><%= username %></li>
        <% 
      } 
      %>
    </ul>
    <% 
  } 
  %>
  <% } %>
  <% } else { %>
  <p><a href="/login">Login</a> to send a message.</p>
  <% } %>
  <hr/>
  <% } %>
</div>
</body>
</html>