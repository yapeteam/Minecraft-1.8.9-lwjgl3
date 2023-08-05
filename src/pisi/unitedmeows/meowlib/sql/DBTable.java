package pisi.unitedmeows.meowlib.sql;

import pisi.unitedmeows.meowlib.etc.Tuple;
import pisi.unitedmeows.meowlib.predefined.STRING;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class DBTable {

    public void delete(DatabaseClient client) {
        Tuple<String, Object> primaryKeyTuple = primaryKeyTuple();
        if (primaryKeyTuple == null) {
            return;
        }
        client.execute("DELETE FROM ^ WHERE ^=? LIMIT 1", tableName(), primaryKeyTuple.getFirst(), primaryKeyTuple.getSecond());
    }

    public boolean insert(DatabaseClient client) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ").append(tableName()).append(" (");


            HashMap<String, Object> values = new HashMap<>();
            int index = 0;
            int last = getClass().getFields().length - 1;
            for (Field field : getClass().getFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    if (field.getAnnotation(PK.class) != null) {
                        continue;
                    }
                    sql.append(column.name()).append(index != last ? "," : STRING.EMPTY);
                    values.put(column.name(), field.get(this));
                }
                index++;
            }

            sql.append(") VALUES (");


            Object[] arguments = new Object[values.size()];
            index = 0;
            for (Map.Entry<String, Object> pair : values.entrySet()) {
                sql.append("^").append(index != arguments.length -1 ? "," : ")");
                arguments[index++] = pair.getValue();
            }

            return client.execute(sql.toString(), arguments);
        } catch (Exception ex) {

        }
        return false;
    }

    public boolean update(DatabaseClient client) {
        try {
            Tuple<String, Object> primaryKeyTuple = primaryKeyTuple();

            if (primaryKeyTuple == null) {
                return false;
            }

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(tableName()).append(" SET ");

            HashMap<String, Object> values = new HashMap<>();
            int index = 0;
            int last = getClass().getFields().length - 1;
            for (Field field : getClass().getFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    if (field.getAnnotation(PK.class) != null) {
                        continue;
                    }
                    sql.append(column.name()).append("=^").append(index != last ? "," : STRING.EMPTY);
                    values.put(column.name(), field.get(this));
                }
                index++;
            }

            Object[] arguments = new Object[values.size() + 1];
            index = 0;

            for (Map.Entry<String, Object> pair : values.entrySet()) {
                arguments[index++] = pair.getValue();
            }

            arguments[values.size()] = primaryKeyTuple.getSecond();
            return client.execute(sql.append(" WHERE ").append(primaryKeyTuple.getFirst()).append("=^").toString(), arguments);
        } catch (Exception ex) {

        }
        return false;
    }

    public <X> X primaryKey() {
        for (Field field : getClass().getFields()) {
            if (field.isAnnotationPresent(PK.class)) {
                try {
                    return (X) field.get(this);
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public Tuple<String, Object> primaryKeyTuple() {
        for (Field field : getClass().getFields()) {
            if (field.isAnnotationPresent(PK.class)) {
                try {
                    String columnName = field.getAnnotation(Column.class).name();
                    Tuple<String, Object> tuple = new Tuple<String, Object>(columnName, field.get(this));
                    return tuple;
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public String tableName() {
        return getClass().getAnnotation(Table.class).name();
    }
}
