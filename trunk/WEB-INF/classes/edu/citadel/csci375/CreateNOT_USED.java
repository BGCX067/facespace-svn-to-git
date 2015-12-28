package edu.citadel.csci375;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CreateNOT_USED extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		HttpSession session = request.getSession();
		Person p = (Person) session.getAttribute("person");
		String address;

		 PrintWriter out = response.getWriter();
		 out.println("<html>");
		 out.println("<head>Debug</head>");
		 out.println(p.getEmail());
		 // out.println(p.getGender());
		 out.println("<br />");
		 out.println(p.getLastName()+":lastname");
		 out.println("</html>");

		if (p == null)
		{
			address = "login.jsp";
		}
		else
		{
			String lastName = p.getLastName();
			String firstName = p.getFirstName();
			String middleName = p.getMiddleName();
			String streetAddress = p.getStreetAddress();
			String city = p.getCity();
			String state = p.getState();
			String zipCode = p.getZipcode();
			String homePhone = p.getHomePhone();
			String cellPhone = p.getCellPhone();
			String email = p.getEmail();
			String bDay = p.getBirthDay();
			String password = p.getPassword();

			if (isValidLastName(lastName)
					&& isValidFirstName(firstName)
					&& isValidMiddleName(middleName) 
					&& isValidCity(city)
					&& isValidState(state)
					&& isValidZipCode(zipCode)
					&& isValidHomePhone(homePhone)
					&& isValidCellPhone(cellPhone) 
					&& isValidEmail(email)
					&& isValidPassword(password)
					&& isValidStreetAddress(streetAddress)
					&& isValidBirthday(bDay))
			{
				DBManager.addPerson(p);
				int oid = p.getOid();
				// session.setAttribute("person", p);
				session.setAttribute("person_oid", new Integer(oid));

				// successful login, redirect to users profile
				address = "profile.jsp?id=" + oid;
			}
			else
			{
				// go back to create.jsp
				session.setAttribute("person", p);
				address = "process.jsp";
			}
		}

		// sendRedirect causes the browser to load the new page
		// encodeRedirectURL allows URL session tracking to be used if necessary
		// response.sendRedirect(response.encodeRedirectURL(address));

	}

	private boolean isValidEmail(String email)
	{
		if (email == null)
			return false;
		else if (!email.matches(".+@.+\\.[a-z]+"))
			return false;
		else
			return true;
	}

	private boolean isValidPassword(String password)
	{
		if (password == null)
			return false;
		else if (password.trim().equals(" "))
			return false;
		else
			return true;
	}

	// Get date format
	private boolean isValidBirthday(String birthday)
	{
		if (birthday == null)
			return false;
		else if (birthday.length() != 10)
			return false;
		else
			return true;
	}

	private boolean isValidCity(String city)
	{
		if (city == null)
			return false;
		else
			return true;
	}

	private boolean isValidFirstName(String firstName)
	{
		if (firstName == null)
			return false;
		else
			return true;
	}

	private boolean isValidLastName(String lastName)
	{
		if (lastName == null)
			return false;
		else
			return true;
	}

	private boolean isValidMiddleName(String middleName)
	{
		if (middleName == null)
			return false;
		else
			return true;
	}

	private boolean isValidState(String state)
	{
		if (state == null)
			return false;
		else if (state.length() != 2)
			return false;
		else
			return true;
	}

	private boolean isValidZipCode(String zipCode)
	{
		if (zipCode == null)
			return false;
		else if (zipCode.length() != 5)
			return false;
		else
			return true;
	}

	private boolean isValidStreetAddress(String streetAddress)
	{
		if (streetAddress == null)
			return false;
		else
			return true;
	}

	// when validating phones we want to instruct user to just
	// put digits in
	private boolean isValidCellPhone(String cellPhone)
	{
		if (cellPhone != null)
		{
			if (cellPhone.length() != 10)
				return false;
			else
				return true;
		}
		else
			return false;
	}

	private boolean isValidHomePhone(String homePhone)
	{
		if (homePhone != null)
		{
			if (homePhone.length() != 10)
				return false;
			else
				return true;
		}
		else
			return false;
	}

}