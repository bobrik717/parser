package com.company.parser;

import com.company.db.ActiveQuery;

import java.util.List;

public class Item extends ActiveQuery {
    public String link;
    public String title;

    public String tableName() {
        return "item";
    }

    public List<String> attributes()
    {
        return List.of("link", "title");
    }
}
