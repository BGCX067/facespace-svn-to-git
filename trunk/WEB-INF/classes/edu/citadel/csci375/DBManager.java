package edu.citadel.csci375;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DBManager
{
	/**
	 * add Person receives the person bean and populates the database with a
	 * person personal information that is asked for in the create.jsp form.
	 * 
	 * @param p
	 */
	public static Person addPerson(Person p)
	{

		String lastName = p.getLastName();
		String firstName = p.getFirstName();
		String middleName = p.getMiddleName();
		String streetAddress = p.getStreetAddress();
		String city = p.getCity();
		String state = p.getState().toUpperCase();
		String zipCode = p.getZipcode();
		String homePhone = p.getHomePhone();
		String cellPhone = p.getCellPhone();
		String email = p.getEmail();
		String gender = p.getGender().toUpperCase();
		String bDay = p.getBirthDay();
		String password = p.getPassword();

		try
		{
			String query = "insert into person"
					+ " (oid, last_nm, first_nm, middle_nm, streetaddress, city, state, zipcode, "
					+ "home_phone, cell_phone, email, gender, b_day, password)"
					+ "values" + "(cadet_seq.nextval, '" + lastName + "', '"
					+ firstName + "', '" + middleName + "', '" + streetAddress
					+ "', '" + city + "', '" + state + "', '" + zipCode
					+ "', '" + homePhone + "', '" + cellPhone + "', '" + email
					+ "', '" + gender + "', " + "to_date('" + bDay
					+ "', 'mm/dd/yyyy'), '" + password + "')";

			System.out.println(query);

			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);

			query = "select oid " + " from person " + " where last_nm = '"
					+ lastName + "' " + "and first_nm = '" + firstName + "' "
					+ "and middle_nm = '" + middleName + "'" + "and email = '"
					+ email + "'";

			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int oid = rs.getInt("oid");
			p.setOid(oid);
			conn.commit();
			stmt.close();
			conn.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * isUniqueEmail will not allow a person to create an account if they are
	 * using the same email that is already stored in the database.
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean isUniqueEmail(String email)
	{
		ResultSet rs;
		boolean isThere = false;
		try
		{
			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();

			String query = "select 1 " + " from person " + " where email = '"
					+ email + "'";

			rs = stmt.executeQuery(query);

			isThere = rs.next();

			stmt.close();
			conn.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return isThere;

	}

	/**
	 * friends will return a list of primary keys for the friends that a person
	 * has accepted.
	 * 
	 * @param oid
	 * @return LinkedList<Integer>
	 */
	public static LinkedList<Integer> friends(int oid)
	{
		LinkedList<Integer> myFriends = new LinkedList<Integer>();

		try
		{
			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();

			String query = "select p.oid, last_nm, first_nm, email"
					+ " from person p"
					+ " join friend_request fr on p.oid = fr.requested_oid"
					+ " join friend f on f.validated_by_oid = fr.oid "
					+ "where fr.requestor_oid in (" + " select oid"
					+ " from person p" + " where oid = " + oid + ")";

			System.out.println("DEBUG:QUERY:\n" + query);
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				Integer friendOid = new Integer(rs.getInt("oid"));
				myFriends.add(friendOid);
			}

			query = "select p.oid, last_nm, first_nm, email" + " from person p"
					+ " join friend_request fr on p.oid = fr.requestor_oid"
					+ " join friend f on f.validated_by_oid = fr.oid "
					+ "where fr.requested_oid in (" + " select oid"
					+ " from person p" + " where oid = " + oid + ")";

			System.out.println("DEBUG:QUERY:\n" + query);
			rs = stmt.executeQuery(query);

			while (rs.next())
			{
				Integer friendOid = new Integer(rs.getInt("oid"));
				myFriends.add(friendOid);
			}
			stmt.close();
			conn.close();

			myFriends.remove(new Integer(oid));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return myFriends;
	}

	/**
	 * friendSearch allows a user to search for someone they may know from the
	 * front end.
	 * 
	 * @param lastName
	 * @param firstName
	 * @return LinkedList<Integer>
	 */
	public static LinkedList<Integer> friendSearch(String lastName,
			String firstName)
	{
		LinkedList<Integer> myFriends = new LinkedList<Integer>();

		try
		{
			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();

			String query = "select oid" + " from person p "
					+ "where lower(first_nm) like lower('%" + firstName + "%')"
					+ " and lower(last_nm) like lower('%" + lastName + "%')";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				Integer friendOid = new Integer(rs.getInt("oid"));
				myFriends.add(friendOid);
			}

			stmt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return myFriends;
	}

	/**
	 * validCombo checks the person's login information to make sure that the
	 * person loggin is in fact a member of facespace.
	 * 
	 * @param email
	 * @param password
	 * @return int
	 */
	public static int validCombo(String email, String password)
	{
		try
		{
			String query = "select password, oid" + " from person"
					+ " where lower(email) = lower('" + email + "')";
			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			String returnedPassword = rs.getString("password");

			if (returnedPassword != null && returnedPassword.equals(password))
			{
				return rs.getInt("oid");
			}
			conn.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * requestFriend allows for someone in the application to choose another
	 * person and request them as a friend
	 * 
	 * @param requestorOid
	 * @param requestedOid
	 */
	public static void requestFriend(int requestorOid, int requestedOid)
	{
		try
		{
			if (requestorOid != requestedOid)
			{
				String query = "select 1 "
						+ "from friend_request "
						+ "where requested_oid = "
						+ requestedOid
						+ "and requestor_oid = "
						+ requestorOid
						+ "and exists( "
						+ "select 1 "
						+ "from friend f "
						+ "join friend_request fr on f.validated_by_oid = fr.oid)";

				ConnectionFactory connFactory = ConnectionFactory.getInstance();
				Connection conn = connFactory.getConnection();
				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(query);
				boolean check1 = rs.next();

				query = "select 1 "
						+ "from friend_request "
						+ "where requested_oid = "
						+ requestorOid
						+ "and requestor_oid = "
						+ requestedOid
						+ "and exists( "
						+ "select 1 "
						+ "from friend f "
						+ "join friend_request fr on f.validated_by_oid = fr.oid)";

				rs = stmt.executeQuery(query);
				boolean check2 = rs.next();

				if (!check1 && !check2)
				{
					query = "insert into friend_request "
							+ "(oid, requestor_oid, requested_oid, friend_comment) "
							+ "values " + "(cadet_seq.nextval, " + requestorOid
							+ "," + requestedOid
							+ ", 'Please accept my friend request')";

					stmt.executeUpdate(query);
					conn.commit();
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * acceptFriend will insert a row into the friend database is the requested
	 * party accepts their friend invitation
	 * 
	 * @param requestorOid
	 * @param requestedOid
	 */
	public static void acceptFriend(int requestorOid, int requestedOid)
	{
		try
		{
			String query = "select oid " + "from friend_request "
					+ "where requested_oid =" + requestedOid
					+ " and requestor_oid =" + requestorOid;

			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();

			int friendRequestOid = rs.getInt("oid");

			query = "insert into friend "
					+ "(oid, validated_by_oid, relationship_oid) " + "values "
					+ "(cadet_seq.nextval, " + friendRequestOid + ", 5)";

			stmt.executeUpdate(query);

			conn.commit();
			conn.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * removeFriend will remove the friend request record that is rejected in
	 * the front end if the requested party does not want to be friends with the
	 * person that has asked to befriend them.
	 * 
	 * @param friendRequestOid
	 */
	public static void removeFriend(int requestorOid, int requestedOid)
	{
		try
		{
			String query = "select oid " + "from friend_request "
					+ "where requested_oid =" + requestedOid
					+ " and requestor_oid =" + requestorOid;

			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();

			int friendRequestOid = rs.getInt("oid");

			query = "delete from friend_request " + "where oid = "
					+ friendRequestOid;

			stmt.executeUpdate(query);
			conn.commit();
			conn.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * amIRequested is the method used in a person's profile to display a new
	 * friend request that they may have received.
	 * 
	 * @param oid
	 * @return
	 */
	public static LinkedList<Integer> amIRequested(int oid)
	{
		LinkedList<Integer> requested = new LinkedList<Integer>();
		ResultSet rs = null;
		try
		{
			String query = "select requestor_oid " + "from friend_request "
					+ "where requested_oid = " + oid + " and oid not in( "
					+ "select fr.oid " + "from friend_request fr "
					+ "join friend f on f.validated_by_oid = fr.oid "
					+ "where requested_oid in ( " + "select oid "
					+ "from person " + "where oid = " + oid + "))";
			System.out.println("amIRequested query:" + query);
			ConnectionFactory connFactory = ConnectionFactory.getInstance();
			Connection conn = connFactory.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next())
			{
				Integer newOid = new Integer(rs.getInt("requestor_oid"));
				requested.add(newOid);
				System.out.println("amIRequested oid:" + newOid);
			}

			conn.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return requested;
	}
}
