<%@page import="edu.citadel.csci375.DBManager"%>
<jsp:useBean id="person" class="edu.citadel.csci375.Person" scope="request">
<jsp:setProperty name="person" property="*"/>
</jsp:useBean>
<% 
   if (person.validate()){
     if (!edu.citadel.csci375.DBManager.isUniqueEmail(person.getEmail())){
       person=edu.citadel.csci375.DBManager.addPerson(person);
       request.getSession().setAttribute("person_oid", person.getOid());
       request.getSession().setAttribute("person", person);
       response.sendRedirect(response.encodeRedirectURL("profile.jsp?id="+person.getOid()));
       }
       else{
           person.setErrorMsg("email", "This email address is already associated with an account.");
           %><jsp:forward page="retry.jsp"/><%
       }
   }
   else { %>
       <jsp:forward page="retry.jsp"/>
       <% } %>