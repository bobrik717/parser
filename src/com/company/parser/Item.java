package com.company.parser;

import com.company.db.ActiveQuery;

import java.util.List;

public class Item extends ActiveQuery {
    public Integer id;
    public String link;
    public String title;
    public String date_create;

    public String tableName() {
        return "item";
    }

    public List<String> attributes()
    {
        return List.of("link", "title");
    }
}
