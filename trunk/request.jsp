<%
//make sure there is a user associated with the session
Integer person_oid=(Integer)session.getAttribute("person_oid");
if (person_oid == null){
  response.sendRedirect("logout.jsp");
}

edu.citadel.csci375.DBManager.requestFriend(person_oid.intValue(), new Integer(request.getParameter("id")).intValue());
response.sendRedirect("profile.jsp?id="+person_oid);

%>