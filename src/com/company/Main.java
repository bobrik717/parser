package com.company;

import com.company.parser.Item;
import com.company.parser.XmlParser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Main {

    private static final String URL = "http://www.oracle.com/ocom/groups/public/@ocom/documents/webcontent/196280.xml";

    public static void main(String[] args) throws SQLException, IllegalAccessException, ParseException {
//        XmlParser parser = new XmlParser();
//        List<Item> items = parser.parseItems(Main.URL);
//        items.forEach(item -> {
//            try {
//                item.save();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });
        Item i = new Item();
        i = (Item) i.find().one().asArray();
        System.out.println(i.title);
    }
}
