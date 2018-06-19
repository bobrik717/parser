package com.company.db;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

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
            if(value.length() > 0) {
                map.put(name, value);
            }
        }
        return this.insert(map);
    }

    public ActiveQuery find(String[] args) {
        return this;
    }

    public ActiveQuery find() {
        Map<String, Class<?>> args = new HashMap<>();
        Field[] publicFields = this.getClass().getFields();
        for(Field field : publicFields) {
            args.put(field.getName(), field.getType());
        }
        this.select(args);
        return this;
    }

    @Override
    public ActiveQuery one() throws SQLException {
        super.one();
        return this;
    }

    public ActiveQuery asArray() throws SQLException, IllegalAccessException, ParseException {
        Field[] publicFields = this.getClass().getFields();
        while (rs.next()) {
            for(Field field : publicFields) {
                String value = rs.getString(field.getName());
                if(field.getType() == int.class) {
                    field.set(this, Integer.parseInt(value));
                }
                if(field.getType() == String.class) {
                    field.set(this, value);
                }
                if(field.getType() == Date.class) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = dateFormat.parse(value);
                    field.set(this, date);
                }
            }
        }
        return this;
    }
}
