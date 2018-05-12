<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="java.util.Collections" %>
<%@ page import="codeu.controller.Notification" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<%
String username = (String) request.getAttribute("username");
String description = (String) request.getAttribute("description");
List<Message> messages = (List<Message>) request.getAttribute("messages");
 User user = (User) UserStore.getInstance().getUser(username);
 Notification notification = (Notification) new Notification(user,"test", "hello user");
user.makeNotification("test", "hello user");
%>

<!DOCTYPE html>
<html>

<head>
	<title>Profile</title>
	<link rel="stylesheet" href="/css/main.css" type="text/css">
	<style>		
  #chat {
   background-color: white;
   height: 200px;
   overflow-y: scroll
 }

 #textbox
 {
  height:200px;
  font-size:14pt;
  background-color:blue
}

#descriptiondisplay
{
 width: 800px;
 word-wrap: break-word;
}

.sidenav {
  width: 250px;
  height: 2450px;
  position: fixed;
  z-index: 1;
  top: 100px;
  left: 10px;
  background-color: #eee;
  overflow-x: hidden;
  padding: 8px 0;
}

.sidenav a {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 25px;
  color: #2196F3;
  display: block;
  background-color: #1e90ff
}

.sidenav a:hover {
  color: #064579;
}
@media screen and (max-height: 500px) {
  .sidenav {padding-top: 0px;}
  .sidenav a {font-size: 18px;}
}
</style>

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
   <% } %>
 </nav>
 <div class="sidenav">
  <h2><%= username %>'s Notifications</h2> 

  <% if(user.hasNotification()){ %>
  <img " src="https://cdn.dribbble.com/users/89254/screenshots/2712352/rate-star.gif" width="250" height="150"> 
  <dialog open> <p>Hi <%= username %>! <br/>You've been surprised by your friends. Check out the groupchat!  <br/> <b>Surprise Title: </b> <i><%=notification.getTitle()%> </i><br/> <b>Message: </b> <i> <%=notification.getMessage() %> </i></p> </dialog>
  <% }  else{ %>
  <img " src="https://pbs.twimg.com/media/DEcpY-_WAAAAMn5.jpg" width="250" height="150"> 
  <dialog open> <p>Hi <%= username %>! <br/>You have no pending notifications! </p> </dialog>
  <% } %>

</div>

<div id="container">

  <% if(request.getAttribute("error") != null){ %>
  <h2 style="color:red"><%= request.getAttribute("error") %></h2>
  <hr/>
  <% } else { %>
  <h1><%= username %>'s Profile Page</h1>

  <hr/>

  <h2>About <%= username %> </h2>
  <p id="descriptiondisplay"><%= description %></p>


  <% if(request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").equals(username)){ %>
  <h3>Edit your About Me (Only you can see)</h3>
  <form action="/profile/<%= username %>" method="POST">
   <textarea rows="6" cols="90" name="editDescription" placeholder="Limit 250 characters" maxlength="250"></textarea><br>

   <button type="submit">Submit</button>
 </form>
 <% } %>

 <hr/>

 <% Collections.sort(messages); %>
 <h2><%= username %>'s Sent Messages</h2>
 <div id="chat">
  <ul>
    <% for (Message message : messages) { %>
    <%
    Date myDate = Date.from(message.getCreationTime());		
    %>
    <li><b><%= myDate.toString() %></b> <%= ": " + message.getContent() %></li>
    <% } %>
  </ul>
</div>

<hr/>

<% } %>

</div>
</body>
</html>  






