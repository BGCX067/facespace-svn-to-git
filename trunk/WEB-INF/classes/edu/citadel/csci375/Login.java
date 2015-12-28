package edu.citadel.csci375;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		HttpSession session = request.getSession();

		// case insensitive
		String email = request.getParameter("email").toLowerCase();
		String password = request.getParameter("password");
		int oid = 0;

		String address = "login.jsp?email=" + email;

		if (validEmail(email) && validPassword(password))
		{
			oid = DBManager.validCombo(email, password);
			if (oid != 0)
			{
				// proccess valid user data
				// associate their oid and data with session
				Person p = new Person(oid);
				session.setAttribute("person", p);

				session.setAttribute("person_oid", new Integer(oid));

				// successful login, redirect to users profile
				address = "profile.jsp?id=" + oid;
			}
		}

		// sendRedirect causes the browser to load the new page
		// encodeRedirectURL allows URL session tracking to be used if necessary
		response.sendRedirect(response.encodeRedirectURL(address));

	}

	private boolean validEmail(String email)
	{
		if (email == null) return false;
		if (!email.matches(".+@.+\\.[a-z]+")) return false;
		return true;
	}

	private boolean validPassword(String password)
	{
		if (password == null) return false;
		if (password.trim().equals("")) return false;
		return true;
	}

}