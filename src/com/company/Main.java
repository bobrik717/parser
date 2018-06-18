package com.company;

import com.company.parser.Item;
import com.company.parser.JavaToMySQL;
import com.company.parser.XmlParser;

import java.sql.SQLException;
import java.util.List;

public class Main {

    private static final String URL = "http://www.oracle.com/ocom/groups/public/@ocom/documents/webcontent/196280.xml";
    public static void main(String[] args) throws SQLException {
        XmlParser parser = new XmlParser();
        List<Item> items = parser.parse(Main.URL);

        JavaToMySQL dbConnection = new JavaToMySQL();

        dbConnection.getConnection().insertItem(items).closeConnection();
    }
}