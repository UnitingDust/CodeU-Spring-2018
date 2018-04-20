<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Message" %>
<%
String username = (String) request.getAttribute("username");
String description = (String) request.getAttribute("description");
List<Message> messages = (List<Message>) request.getAttribute("messages");
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
 
   <div id="container">
   
    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
        <hr/>
    <% } else { %>
    	<h1><%= username %>'s Profile Page</h1>
    
    	<hr/>
    	
    	<h2>About <%= username %> </h2>
    	<p><%= description %></p>
    	
    	
    	<% if(request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").equals(username)){ %>
    		<form action="/profile/<%= username %>" method="POST">
    			<textarea rows="4" cols="50" name="editDescription" placeholder="Limit 200 characters" maxlength="200"></textarea><br>
    			
    			<%--
          		<div class="form-group">
            		  <label class="form-control-label">Edit your about me: (Only you can see this)</label> 
          		<input id="textbox" type="text" name="editDescription">
          		
        		</div>
				--%>
				
        		<button type="submit">Submit</button>
      		</form>
    	<% } %>
    	
    	<hr/>
    	
    	<h2><%= username %>'s Sent Messages</h2>
    	<div id="chat">
      		<ul>
    		<% for (Message message : messages) { %>
     			<li><%= message.getContent() %></li>
    		<% } %>
      		</ul>
    	</div>

    	<hr/>
    	
    <% } %>
    
    </div>
 </body>
</html>  
    
 
    
    
    
    
    