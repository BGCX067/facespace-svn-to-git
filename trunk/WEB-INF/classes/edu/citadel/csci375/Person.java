package edu.citadel.csci375;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Person
{
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private String city;
	private String state;
	private String zipcode;
	private String streetAddress;
	private String birthDay;
	private String gender = ""; // TODO check all locations for valid values
	private int oid;
	private String homePhone;
	private String cellPhone;
	private String password;
	private Hashtable errors;

	public boolean validate()
	{
		boolean noProblems = true;
		if (firstName.equals(""))
		{
			errors.put("firstName", "Please enter your first name");
			firstName = "";
			noProblems = false;
		}
		if (lastName.equals(""))
		{
			errors.put("lastName", "Please enter your last name");
			lastName = "";
			noProblems = false;
		}
		if (middleName.equals(""))
		{
			errors.put("middleName", "Please enter your middle name");
			middleName = "";
			noProblems = false;
		}
		if (city.equals(""))
		{
			errors.put("city", "Please enter the city you live in");
			city = "";
			noProblems = false;
		}
		if (state.equals("") || state.length() != 2)
		{
			errors.put("state", "Please enter the state you live in");
			state = "";
			noProblems = false;
		}
		if (zipcode.equals("") || zipcode.length() != 5)
		{
			errors.put("zipcode", "Please enter the zipcode you live in");
			zipcode = "";
			noProblems = false;
		}
		if (homePhone.equals("") || homePhone.length() != 10)
		{
			errors
					.put("homePhone",
							"Please enter your 10 digit phone number without periods or dashes");
			homePhone = "";
			noProblems = false;
		}
		if (cellPhone.equals("") || cellPhone.length() != 10)
		{
			errors
					.put("cellPhone",
							"Please enter your 10 digit phone number without periods or dashes");
			cellPhone = "";
			noProblems = false;
		}
		if (email.equals("") || email.indexOf('@') == -1)
		{
			errors.put("email",
					"Please enter your email in the form xxx@server.com");
			email = "";
			noProblems = false;
		}
		if (password.equals("") || password.length() < 6
				|| password.length() > 15)
		{
			errors
					.put(
							"password",
							"Please enter a password that is at least 6 characters long but no more than 15");
			password = "";
			noProblems = false;
		}
		if (streetAddress.equals(""))
		{
			errors.put("streetAddress", "Please enter your street address");
			streetAddress = "";
			noProblems = false;
		}
		if (birthDay.equals("") || birthDay.length() != 10)
		{

			errors.put("birthDay",
					"Please enter your birthday in the forom mm/dd/yyyy");
			birthDay = "";
			noProblems = false;
		}
		return noProblems;
	}

	public void setErrorMsg(String id, String msg)
	{
		errors.put(id, msg);
	}

	public String getErrorMsg(String s)
	{
		String msg = (String) errors.get(s.trim());
		if (msg == null)
			return "";
		else
			return msg;
	}

	public Person()
	{
		this.firstName = "";
		this.lastName = "";
		this.middleName = "";
		this.email = "";
		this.city = "";
		this.state = "";
		this.zipcode = "";
		this.streetAddress = "";
		this.birthDay = "";
		this.gender = "";
		this.password = "";
		homePhone = "";
		cellPhone = "";
		errors = new Hashtable();
		// 0 is default value, means object not in sync with database
		oid = 0;

	}

	// does not set oid, oid can only be chosen by the database
	// public Person(String firstName, String lastName, String middleName,
	// String email, String city, String state, String zipcode,
	// String streetAddress, String birthDay, String gender, String password)
	// {
	// this.firstName = firstName;
	// this.lastName = lastName;
	// this.middleName = middleName;
	// this.email = email;
	// this.city = city;
	// this.state = state;
	// this.zipcode = zipcode;
	// this.streetAddress = streetAddress;
	// this.birthDay = birthDay;
	// this.gender = gender;
	// this.password = password;
	// errors = new Hashtable();
	// oid = 0;
	// }

	// TODO complete constructor from database using oid
	// check oid to see if it fails
	public Person(int oid)
	{
		try
		{
			String query = "select last_nm, first_nm, middle_nm, streetaddress, city, state, zipcode, "
					+ "home_phone, cell_phone, email, gender, b_day, password, TO_CHAR(B_DAY, 'Month DD, YYYY')"
					+ " AS BIRTH_DAY_FORMATTED from person where oid = '"
					+ oid
					+ "'";
			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next())
			{
				firstName = rs.getString("FIRST_NM");
				lastName = rs.getString("LAST_NM");
				middleName = rs.getString("MIDDLE_NM");
				email = rs.getString("EMAIL");
				city = rs.getString("CITY");
				state = rs.getString("STATE");
				zipcode = rs.getString("ZIPCODE");
				streetAddress = rs.getString("STREETADDRESS");
				birthDay = rs.getString("BIRTH_DAY_FORMATTED");
				// System.out.println("DEBUG:birthDay:"+birthDay);
				gender = rs.getString("GENDER");
				cellPhone = rs.getString("CELL_PHONE");
				homePhone = rs.getString("HOME_PHONE");
				password = rs.getString("PASSWORD");

				this.oid = oid;
				errors = new Hashtable();
			}
			// oid==0 if something goes wrong
			conn.close();
		}
		catch (SQLException e)
		{
		}
	}

	/**
	 * Tells if the object is in sync (holds person data from the database).
	 * 
	 * @return true if valid data is in the object.
	 */
	public boolean isInSync()
	{
		return (oid == 0) ? false : true;
	}

	/*
	 * returns 0 if object not in sync
	 */
	public int getOid()
	{
		return oid;
	}

	public String getGender()
	{
		return gender;
	}

	public String getGenderString()
	{
		return (gender.toLowerCase().equals("m")) ? "Male" : "Female";
	}

	public String[] getGenderChoices()
	{
		String[] result = { "Male", "Female" };
		return result;
	}

	public String[] getGenderValues()
	{
		String[] result = { "m", "f" };
		return result;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	// full name
	public String getName()
	{
		return firstName + " " + lastName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public String getEmail()
	{
		return email;
	}

	public String getCity()
	{
		return city;
	}

	public String getState()
	{
		return state;
	}

	public String getZipcode()
	{
		return zipcode;
	}

	public String getStreetAddress()
	{
		return streetAddress;
	}

	// TODO format here
	public String getBirthDay()
	{
		return birthDay;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public void setZipcode(String zipcoad)
	{
		this.zipcode = zipcoad;
	}

	public void setStreetAddress(String streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	public void setBirthDay(String birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getCellPhone()
	{
		return cellPhone;
	}

	public void setCellPhone(String cellPhone)
	{
		this.cellPhone = cellPhone;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setOid(int oid)
	{
		this.oid = oid;
	}

}
