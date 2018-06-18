package com.company.db;

import com.company.cofigs.Params;
import com.company.parser.Item;
import com.mysql.cj.util.StringUtils;

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
            con = DriverManager.getConnection(Params.url, Params.user, Params.password);

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

    /**
     * @return String
     */
    public String tableName() {
        return "";
    }

    public DbConnection openDbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // opening database connection to MySQL server
            con = DriverManager.getConnection(Params.url, Params.user, Params.password);

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
            if(pstmt != null) {
                pstmt.close();
            }
        } catch(SQLException se) { /*can't do anything */ }
        try {
            if(rs != null) {
                rs.close();
            }
        } catch(SQLException se) { /*can't do anything */ }
    }

    public Boolean insert(Map<String,String> args) {
        openDbConnection();
        try {
            String sql = "INSERT INTO " + Params.DB_NAME + "." + this.tableName() + " ";
            StringBuilder rows = new StringBuilder("(");
            StringBuilder values = new StringBuilder(" VALUES (");
            for (Map.Entry<String, String> entry : args.entrySet()) {
                rows.append(entry.getKey()).append(",");
                values.append("?,");
            }
            sql += rows.toString().replaceAll(",$", "") + ")" + values.toString().replaceAll(",$", "") + ")" + ";";
            pstmt = con.prepareStatement(sql);
            Integer i = 1;
            for (Map.Entry<String, String> entry : args.entrySet()) {
                pstmt.setString(i, entry.getValue());
                i++;
            }

            return pstmt.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            closeDbConnection();
        }
        return false;
    }
}
