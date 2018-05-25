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
<!DOCTYPE html>
<html>
<head>
  <title>Gossip Guru</title>
  <link href="https://fonts.googleapis.com/css?family=Oxygen|Pacifico" rel="stylesheet">
  <link rel="stylesheet" href="/css/style.css">

  <style>

    .card {
      box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
      max-width: 400px;
      margin: auto;
      text-align: center;
      font-family: arial;
    }

    .title {
      color: grey;
      font-size: 18px;
    }


.column {
    float: left;
    width: 25%;
    padding: 5px;
}

/* Clearfix (clear floats) */
.row::after {
    content: "";
    clear: both;
    display: table;
}

/* Responsive layout - makes the three columns stack on top of each other instead of next to each other */
@media screen and (max-width: 500px) {
    .column {
        width: 100%;
    }
}

    * {
      box-sizing: border-box;
    }

body {
    background-color: #D59EC8;
    font-family: Helvetica, sans-serif;
    }

    /* The actual timeline (the vertical ruler) */
    .timeline {
      position: relative;
      max-width: 1200px;
      margin: 0 auto;
    }

    /* The actual timeline (the vertical ruler) */
    .timeline::after {
      content: '';
      position: absolute;
      width: 6px;
      background-color: white;
      top: 0;
      bottom: 0;
      left: 50%;
      margin-left: -3px;
    }

    /* Container around content */
    .container {
      padding: 10px 40px;
      position: relative;
      background-color: inherit;
      width: 50%;
    }

    /* The circles on the timeline */
    .container::after {
      content: '';
      position: absolute;
      width: 25px;
      height: 25px;
      right: -17px;
      background-color: white;
      border: 4px solid #FF9F55;
      top: 15px;
      border-radius: 50%;
      z-index: 1;
    }

    /* Place the container to the left */
    .left {
      left: 0;
    }

    /* Place the container to the right */
    .right {
      left: 50%;
    }

    /* Add arrows to the left container (pointing right) */
    .left::before {
      content: " ";
      height: 0;
      position: absolute;
      top: 22px;
      width: 0;
      z-index: 1;
      right: 30px;
      border: medium solid white;
      border-width: 10px 0 10px 10px;
      border-color: transparent transparent transparent white;
    }

    /* Add arrows to the right container (pointing left) */
    .right::before {
      content: " ";
      height: 0;
      position: absolute;
      top: 22px;
      width: 0;
      z-index: 1;
      left: 30px;
      border: medium solid white;
      border-width: 10px 10px 10px 0;
      border-color: transparent white transparent transparent;
    }

    /* Fix the circle for containers on the right side */
    .right::after {
      left: -16px;
    }

    /* The actual content */
    .content {
      padding: 20px 30px;
      background-color: white;
      position: relative;
      border-radius: 6px;
    }

    /* Media queries - Responsive timeline on screens less than 600px wide */
    @media screen and (max-width: 600px) {
      /* Place the timelime to the left */
      .timeline::after {
        left: 31px;
      }

      /* Full-width containers */
      .container {
        width: 100%;
        padding-left: 70px;
        padding-right: 25px;
      }

      /* Make sure that all arrows are pointing leftwards */
      .container::before {
        left: 60px;
        border: medium solid white;
        border-width: 10px 10px 10px 0;
        border-color: transparent white transparent transparent;
      }

      /* Make sure all circles are at the same spot */
      .left::after, .right::after {
        left: 15px;
      }

      /* Make all right containers behave like the left ones */
      .right {
        left: 0%;
      }
    }
  </style>
</head>
<body>

  <nav>
    <a id="navTitle" href="/">Gossip Guru</a>
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
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

       <h1>About Gossip Guru</h1>
    <p>
      This chat app was created by CodeU Team 32 to make connecting with friends and family more fun!!</p>

      <li><strong> Lets meet the team: </strong></li>

        <div class="card">
        <img src="https://3.bp.blogspot.com/-fm0Cg5WFsy8/WF6YWJyUvuI/AAAAAAAFof0/nRsq3JLfwNwPqZA20fPDFAH8aOUFLH7nACLcB/s1600/AW356234_04.gif" alt="Ryan" style="width:100%">
        <h2>Ryan Luo</h2>
        <p class="title">Project Advisor</p>
        <p>Google, Software Engineer</p>
      </div>

 <div class="row">
  <div class="column">
        <div class="card">
        <img src="https://3.bp.blogspot.com/-fm0Cg5WFsy8/WF6YWJyUvuI/AAAAAAAFof0/nRsq3JLfwNwPqZA20fPDFAH8aOUFLH7nACLcB/s1600/AW356234_04.gif" alt="Johanna" style="width:100%">
        <h2>Johanna Brown</h2>
        <p class="title">Project Developer</p>
        <p>Mount Holyoke College, Sophomore</p>
      </div>
  </div>
  <div class="column">
            <div class="card">
        <img src="https://3.bp.blogspot.com/-fm0Cg5WFsy8/WF6YWJyUvuI/AAAAAAAFof0/nRsq3JLfwNwPqZA20fPDFAH8aOUFLH7nACLcB/s1600/AW356234_04.gif" alt="Jess" style="width:100%">
        <h2>Jess Ho</h2>
        <p class="title">Project Developer</p>
        <p>Princeton University, Sophomore</p>
      </div>
  </div>
  <div class="column">
           <div class="card">
        <img src="https://3.bp.blogspot.com/-fm0Cg5WFsy8/WF6YWJyUvuI/AAAAAAAFof0/nRsq3JLfwNwPqZA20fPDFAH8aOUFLH7nACLcB/s1600/AW356234_04.gif" alt="Isabella" style="width:100%">
        <h2>Isabella Salvi</h2>
        <p class="title">Project Developer</p>
        <p>Penn State University, Sophomore </p>
      </div>
  </div>

   <div class="column">
           <div class="card">
        <img src="https://3.bp.blogspot.com/-fm0Cg5WFsy8/WF6YWJyUvuI/AAAAAAAFof0/nRsq3JLfwNwPqZA20fPDFAH8aOUFLH7nACLcB/s1600/AW356234_04.gif" alt="Angel" style="width:100%">
        <h2>Angel Chen</h2>
        <p class="title">Project Developer</p>
        <p>Lehigh University, Sophomore</p>
      </div>
  </div>
</div>

  <h1><strong> Progress and Timeline </strong></hi>
      </h1>
      <div>
        <div class="timeline">
          <div class="container left">
            <div class="content">

              <h2>Week 1 & 2</h2>
              <p>Introductions and Complete Project 0: Set up Github Repo, workspace, cloud project, update homepage and about page, add a Registration page </p>
            </div>
          </div>
          <div class="container right">
            <div class="content">
              <h2>Week 3 & 4</h2>
              <p>Add Stylized text feature and profile page</p>
            </div>
          </div>
          <div class="container left">
            <div class="content">
              <h2>Week 5 & 6</h2>
              <p>Created demo videos for Project 2 implementations; Brainstorm for open project and create design doc</p>
            </div>
          </div>
          <div class="container right">
            <div class="content">
              <h2>Week 7 & 8</h2>
              <p>Wrapped up Project 2; Work on open project and decide on implementations: calendar, notification, and public vs private conversations</p>
            </div>
          </div>
          <div class="container left">
            <div class="content">
              <h2>Week 9 & 10</h2>
              <p> Had our 5 hour Team 32 Hackathon; Created calendar and notification feature</p>
            </div>
          </div>
          <div class="container right">
            <div class="content">
              <h2>Week 11 - 12</h2>
              <p>Implement notification feature, logout feature, polish UI, and fix backend loopholes, finalize design doc and submit final project</p>
            </div>
          </div>
        </div>

      </div>
</body>
</html>
