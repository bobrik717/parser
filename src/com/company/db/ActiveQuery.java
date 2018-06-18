package com.company.db;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveQuery extends DbConnection {

    public List<String> attributes()
    {
        return List.of("");
    }

    public Boolean save() throws IllegalAccessException {
        Field[] publicFields = this.getClass().getFields();
        Map<String,String> map = new HashMap<>();
        for(Field field : publicFields) {
            String name = field.getName();
            String value = (String) field.get(this);
            map.put(name, value);
        }
        return this.insert(map);
    }
}
