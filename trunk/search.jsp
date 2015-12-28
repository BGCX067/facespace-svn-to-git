<%
// make sure there is a user associated with the session
Integer person_oid=(Integer)session.getAttribute("person_oid");
if (person_oid == null){
  response.sendRedirect("login.jsp");
}
// make sure parameter is good
edu.citadel.csci375.Person profile = (edu.citadel.csci375.Person)session.getAttribute("person");
if (profile == null){
    profile = new edu.citadel.csci375.Person(person_oid);
    session.setAttribute("person", profile);
}
java.util.LinkedList<Integer> resultList = edu.citadel.csci375.DBManager.friendSearch(request.getParameter("last"),request.getParameter("first"));
java.util.LinkedList<edu.citadel.csci375.Person> resultPersonList = new java.util.LinkedList<edu.citadel.csci375.Person>();
for (Integer oid : resultList)
{
	resultPersonList.add(new edu.citadel.csci375.Person(oid.intValue()));
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>FaceSpace | Search Results</title>
<link type="text/css" href="style.css" rel="stylesheet"/>
</head>
<body>
  <div id="page">
    <div id="panel">
      <div id="logo">
        <img src="logo.gif" />
      </div>
      <div id="stuff">
        <form action="search.jsp">
        <div class="formRow">
          <label class="" for="first">First Name:</label>
          <input id="first" name="first" type="text" value='' />
        </div>
        <div class="formRow">
          <label class="" for="last">Last Name:</label>
          <input id="last" name="last" type="text" value='' />
        </div>
        <div class="formRow">
          <input id="search" class="inputButton" type="submit" value="Search"/>
        </div>
        </form>
      </div>
    </div>
    <div id="main">
      <div id="header">
        <a href="profile.jsp?id=<%= person_oid.intValue() %>">Profile</a>|
        <a href="friends.jsp">Friends</a>|
        <a href="log_out.jsp">Logout</a>
      </div>
      <div id="content">
        
        
        <div id="narrow_column">
        </div>
        <div id="wide_column">
          <div id="title">
            <h2><span id="name">Search Results</span></h2>
            <br />
          </div>

<% for (edu.citadel.csci375.Person p : resultPersonList){ %>
  <div class="item"><a href="profile.jsp?id=<%= p.getOid()%>"><%= p.getName() %></a> <a href="request.jsp?id=<%= p.getOid() %>">Request friend</a></div>
 
 <% } %>
        </div>
        
        
        
      </div>
      <div id="footer">
        Its just another nifty feature.
      </div>
    </div>

  </div>
</body>
</html>