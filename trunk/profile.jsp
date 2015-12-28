<%
// make sure there is a user associated with the session
Integer person_oid=(Integer)session.getAttribute("person_oid");
if (person_oid == null){
  response.sendRedirect("login.jsp");
}
// make sure parameter is good
edu.citadel.csci375.Person profile;
Integer id = new Integer(request.getParameter("id"));

if (id!=null && id.equals(person_oid)){
    // if user is requesting his own page, don't rebuild his data.
    // use the bean attached to his session.
    profile = (edu.citadel.csci375.Person)session.getAttribute("person");
    
    if (profile == null){
        profile = new edu.citadel.csci375.Person(person_oid);
        session.setAttribute("person", profile);
    }
} else {
    profile = new edu.citadel.csci375.Person(id.intValue());
}
System.out.println("profile.jsp");
//System.out.println("DEBUG:birthDay:"+profile.getGender());
%>
<%-- <jsp:useBean id="profile" type="edu.citadel.csci375.Person" scope="page"/> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>FaceSpace | <%= profile.getName() %></title>
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
            <span id="name"><h2><%= profile.getName() %></h2></span>
            <br>
          </div>
          <div id="basic_info">
            <div class="profileLabel">Gender:</div><%= profile.getGenderString() %> <br>
            <div class="profileLabel">Birthday:</div><%= profile.getBirthDay() %> <br>
            <div class="profileLabel">State:</div><%= profile.getState() %> <br>
          </div>
          <div id="info_box" class="profile_box">
           <div class="box_head">
           Information
           </div>
           <div class="box_content">
           <h4>Contact Info</h4>
           <div class="profileLabel">Email:</div><%= profile.getEmail() %> <br>
           <div class="profileLabel">Home Phone:</div><%= profile.getHomePhone() %> <br>
           <div class="profileLabel">Cell Phone:</div><%= profile.getCellPhone() %> <br>
           <div class="profileLabel">Address:</div><%= profile.getStreetAddress() %><br>
           <div class="profileLabel">City:</div><%= profile.getCity() %><br>
           <div class="profileLabel">State:</div><%= profile.getState() %><br>
           <div class="profileLabel">Zip:</div><%= profile.getZipcode() %><br>
           </div>
          </div>
          
          <% if (id.equals(person_oid)){ 
        	  java.util.LinkedList<Integer> requestsOidList = edu.citadel.csci375.DBManager.amIRequested(person_oid);
              if (!requestsOidList.isEmpty()) { 
                  java.util.LinkedList<edu.citadel.csci375.Person> requestsPersonList = new java.util.LinkedList<edu.citadel.csci375.Person>();
                  for (Integer oid : requestsOidList){
                	  System.out.println("requestsOid:"+oid);
                	  requestsPersonList.add(new edu.citadel.csci375.Person(oid.intValue()));
                      //System.out.println("DEBUG\noid added to friendsPersonList:"+oid);
                  }%>
          <div id="request_box" class="profile_box">
           <div class="box_head">
           Friend Requests
           </div>
           <div class="box_content">
           <% for (edu.citadel.csci375.Person p : requestsPersonList){ %>
           <div class="box_item"><a href="profile.jsp?id=<%= p.getOid()%>"><%= p.getName() %></a> <a href="accept.jsp?id=<%= p.getOid() %>">Accept</a> <a href="deny.jsp?id=<%= p.getOid() %>">Deny</a></div>
           <%   } // for %>
           </div>
          </div>
          
          <%    } // if not empty
             } // if id is oid%>
          
        </div>
        
        
        
      </div>
      <div id="footer">
        This project deserves an A+, Dr. Moore.
      </div>
    </div>

  </div>
</body>
</html>