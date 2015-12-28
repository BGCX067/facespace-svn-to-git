<%@page import="edu.citadel.csci375.DBManager"%>
<%
//make sure there is a user associated with the session
Integer person_oid=(Integer)session.getAttribute("person_oid");
if (person_oid == null){
  response.sendRedirect("logout.jsp");
}
DBManager.acceptFriend(new Integer(request.getParameter("id")).intValue(),person_oid.intValue());
response.sendRedirect("profile.jsp?id="+person_oid);
%>