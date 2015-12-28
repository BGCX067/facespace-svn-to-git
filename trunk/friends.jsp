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
// Integer id = new Integer(request.getParameter("id"));

System.out.println("DEBUG\nperson_oid:"+person_oid);
java.util.LinkedList<Integer> friendsOidList = edu.citadel.csci375.DBManager.friends(person_oid.intValue());
System.out.println("DEBUG\nfriendOidList isEmpty:"+friendsOidList.isEmpty());
java.util.LinkedList<edu.citadel.csci375.Person> friendsPersonList = new java.util.LinkedList<edu.citadel.csci375.Person>();
for (Integer oid : friendsOidList)
{
    friendsPersonList.add(new edu.citadel.csci375.Person(oid.intValue()));
    System.out.println("DEBUG\noid added to friendsPersonList:"+oid);
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>FaceSpace | <%= profile.getName() %>'s Friends</title>
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
            <h2><span id="name"><%= profile.getName() %>'s Friends</span></h2>
            <br />
          </div>

<% for (edu.citadel.csci375.Person p : friendsPersonList){ %>
  <div class="item"><a href="profile.jsp?id=<%= p.getOid()%>"><%= p.getName() %></a></div>
 
 <% } %>
        </div>
        
        
        
      </div>
      <div id="footer">
        A+ A+ A+ A+ ... &lt;/subliminal&gt;
      </div>
    </div>

  </div>
</body>
</html>