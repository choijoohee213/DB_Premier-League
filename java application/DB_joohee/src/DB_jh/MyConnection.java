package DB_jh;

import java.sql.*;

public class MyConnection {

   public static Connection mackConnection() {
	 // public static StringBuilder sb = new StringBuilder();      
	  Connection con=null;
      try {

         // load and register the Driver
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

         String url = "jdbc:sqlserver://DESKTOP-68PTQOL\\SQLEXPRESS:1433;databaseName=premier league";

         con = DriverManager.getConnection(url, "joohee", "12345");

         System.out.println("Database connection Established Succusfully");
         System.out.println(" Connected!..");
        // sb.append("Connection Established...\n");

      } catch (Exception e) {
    	 System.out.println(e.getMessage());
    	 System.out.println("connection failed");
       //  sb.append("Connection failed ..."+"\n");
   
      }
      return con;

   }

}