<!DOCTYPE html>
<html>
<head>
 <title>Register</title>
  <link href="https://fonts.googleapis.com/css?family=Oxygen|Pacifico" rel="stylesheet">
 <link rel="stylesheet" href="/css/style.css">
 <style>
   label {
     display: inline-block;
     width: 100px;
   }
 </style>
</head>
<body>
<div class="wrapper">
  <div class="main-container">
    <br><br>
   <h1>Greetings from the Guru</h1>
   <br><br>
   <% if(request.getAttribute("error") != null){ %>
       <h2 style="color:black"><%= request.getAttribute("error") %></h2>
   <% } %>
   <form class="main-form" action="/register" method="POST">
     <input type="text" name="username" id="username" placeholder="Username">
     <input type="password" name="password" id="password" placeholder="Password">
     <button type="submit" id="login-button">Register</button>
   </form>
 </div>
 <ul class="bg-bubbles">
 <li></li>
 <li></li>
 <li></li>
 <li></li>
 <li></li>
 <li></li>
 <li></li>
 <li></li>
 <li></li>
 <li></li>
 </ul>
</div>
</body>
</html>
