<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>FaceSpace | Welcome to FaceSpace</title>
<link type="text/css" href="style.css" rel="stylesheet"/>
</head>
<body>
  <div id="page">
    <div id="panel">
      <div id="logo">
        <img src="logo.gif" />
      </div>
      <div id="stuff">
        Welcome Dr. Moore! You've finaly reached Web 3.0! Enjoy your stay.<br /><br /> -- The FaceSpace development team.
      </div>
    </div>
    <div id="main">

      <div id="content">

        <h1>FaceSpace Login</h1>

      <form action="Login" method="post">
       <div class="subContent">
        <div class="formRow">
          <label class="login_lbl" for="email">Email:</label>
          <% String email = request.getParameter("email"); %>
          <input id="email" name="email" type="text" value='<%= (null==email)?"":email %>' />
        </div>
        <div class="formRow">
          <label class="login_lbl" for="password">Password:</label>
          <input id="password" name="password" type="password"/>
        </div>
        <div class="formRow">
          <input id="login" class="inputButton" type="submit" value="Login"/>
        </div>
       </div>
      </form>
      <h1>New to FaceSpace?</h1>
      <div class="subContent">
        <a href="create.jsp">Create an account now!</a>
      </div>
      
      </div>
      <div id="footer">
        I really should find something to put down here.
      </div>
    </div>

  </div>
</body>
</html>