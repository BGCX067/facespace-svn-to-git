package edu.citadel.csci375;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * A class for managing JDBC connections for the project. <br/>
 * (implements the Singleton Pattern)
 */
public class ConnectionFactory
  {
    private static final String DB_PROPERTIES_FILE_NAME = "/db.properties";

    // the one and only instance
    private static ConnectionFactory instance = null;

    private String url;  // = jdbc:oracle:thin:@macs.citadel.edu:1521:CSCI
    private String user; //daiglel
    private String password;  //bf2001


    protected ConnectionFactory(String url, String user, String password)
        throws IOException, SQLException
      {
        this.url      = url;
        this.user     = user;
        this.password = password;
      }


    public static synchronized ConnectionFactory getInstance()
      {
        if (instance == null)
          {
            try
              {
                Properties  props = new Properties();
                InputStream inStream;

                // load the default properties
                inStream = ConnectionFactory.class.getResourceAsStream(DB_PROPERTIES_FILE_NAME);
                if (inStream != null)
                  {
                    props.load(inStream);
                    inStream.close();
                  }
                else
                    throw new IOException("Property file " + DB_PROPERTIES_FILE_NAME
                                        + " not found");

                String url      = props.getProperty("dbUrl");
                String user     = props.getProperty("dbUser");
                String password = props.getProperty("dbPassword");

                Class.forName(props.getProperty("databaseDriverName"));

                instance = new ConnectionFactory(url, user, password);
              }
            catch (Exception ex)
              {
                ex.printStackTrace();
              }
          }

        return instance;
      }


    public synchronized Connection getConnection() throws SQLException
      {
        return DriverManager.getConnection(url, user, password);
      }
  }
