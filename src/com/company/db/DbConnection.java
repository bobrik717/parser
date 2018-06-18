package com.company.db;

import com.company.parser.Item;

import java.sql.*;
import java.util.List;
import java.util.Map;


/**
 * Simple Java program to connect to MySQL database running on localhost and
 * running SELECT and INSERT query to retrieve and add data.
 * @author Javin Paul
 */
public class DbConnection {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement pstmt;

     public static void main(String args[]) {
        String query = "select count(*) from item";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total number of books in the table : " + count);
            }

        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    public String tableName() {
        return "";
    }

    public DbConnection openDbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }

        return this;
    }

    public void closeDbConnection() {
        try {
            con.close();
        } catch(SQLException se) { /*can't do anything */ }
        try {
            stmt.close();
        } catch(SQLException se) { /*can't do anything */ }
        try {
            pstmt.close();
        } catch(SQLException se) { /*can't do anything */ }
        try {
            if(rs != null) {
                rs.close();
            }
        } catch(SQLException se) { /*can't do anything */ }
    }

    public DbConnection insertItem(List<Item> models) throws SQLException {
        for (Item item : models) {
            pstmt = con.prepareStatement("INSERT INTO test.item (title, link)  VALUES (?, ?);");
            pstmt.setString(1, item.title);
            pstmt.setString(2, item.link);
            pstmt.execute();
        }
        return  this;
    }

    public Boolean insert(Map<String,String> args) {
        String sql = "";
        for (Map.Entry<String, String> entry : args.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        return true;
    }
}