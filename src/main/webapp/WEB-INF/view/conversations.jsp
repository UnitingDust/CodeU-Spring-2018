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
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>

<!DOCTYPE html>
<html>
<head>
  <title>Conversations</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
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
    <% } %>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <h1>New Conversation</h1>
      <form action="/conversations" method="POST">
          <div class="form-group">
            <label class="form-control-label">Title:</label>
          <input type="text" name="conversationTitle">
        </div>
        <div class="form-group">
            <label class="form-control-label">Type:</label>
            <input type="radio" name="conversationType" value="public"> Public
            <input type="radio" name="conversationType" value="private"> Private
        </div>

        <button type="submit">Create</button>
      </form>

      <hr/>
    <% } %>
      <div style="float:left; width: 50%">
    <h1>Public Conversations</h1>

    <%
    List<Conversation> publicConversations =
      (List<Conversation>) request.getAttribute("public-conversations");
    if(publicConversations == null || publicConversations.isEmpty()){
    %>
      <p>Create a public conversation to get started.</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for(Conversation conversation : publicConversations){
    %>
      <li><a href="/chat/<%= conversation.getTitle() %>">
        <%= conversation.getTitle() %></a></li>
    <%
      }
    %>
      </ul>
    <%
    }
    %>
  </div>

  <div style="float: right; width: 50%;">

    <h1>Private Conversations</h1>

    <%
    List<Conversation> privateConversations =
      (List<Conversation>) request.getAttribute("private-conversations");
    if(privateConversations == null || privateConversations.isEmpty()){
    %>
      <p>Create a private conversation to get started.</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for(Conversation conversation : privateConversations){
    %>
      <li><a href="/chat/<%= conversation.getTitle() %>">
        <%= conversation.getTitle() %></a></li>
    <%
      }
    %>
      </ul>
    <%
    }
    %>
  </div>
  </div>
</body>
</html>
