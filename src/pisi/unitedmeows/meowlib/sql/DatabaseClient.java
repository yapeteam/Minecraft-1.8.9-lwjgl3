package pisi.unitedmeows.meowlib.sql;


import pisi.unitedmeows.meowlib.predefined.STRING;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseClient {

    private Connection connection;
    private boolean connected;
    private final Object actionLock = new Object();

    public DatabaseClient(String host, String username, String password, String dbName) {
        this(host, 3306, username, password, dbName);
    }

    public DatabaseClient(String host, int port, String username, String password, String database) {
        try {
            synchronized (this) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&useConfigs=maxPerformance", username, password);
                connected = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            connected = false;
        }
    }



    public boolean execute(String sql, Object... arguments) {
        try {
            PreparedStatement command = arguments == null ? connection().prepareStatement(sql) : createCommand(sql, arguments);
            return command.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public <X> X first(Class<X> type, String query, Object... arguments) {
        try {
            PreparedStatement statement = arguments == null
                    ? connection.prepareStatement(query) : createCommand(query, arguments);

            ResultSet resultSet;

            X instance = type.newInstance();
            synchronized (actionLock) {
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    fillObject(resultSet, instance, type);
                }
            }
            resultSet.close();
            return instance;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public <X> List<X> select(Class<X> type, String query, Object... arguments) {
        try {
            PreparedStatement statement = arguments == null
                    ? connection.prepareStatement(query) : createCommand(query, arguments);

            ResultSet resultSet;

            List<X> list = new ArrayList<>();
            synchronized (actionLock) {
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    X instance = type.newInstance();
                    fillObject(resultSet, instance, type);
                    list.add(instance);
                }
            }
            resultSet.close();
            return list;
        } catch (Exception ex) {

        }
        return new ArrayList<>();
    }

    public Object[] firstRaw(String query, Object... arguments) {
        try {
            PreparedStatement command = arguments == null ? connection().prepareStatement(query) : createCommand(query, arguments);

            ResultSet resultSet;
            synchronized (actionLock) {
                resultSet = command.executeQuery();
                if (resultSet.next()) {
                    Object[] objects = new Object[resultSet.getMetaData().getColumnCount()];
                    for (int i = 0; i < objects.length; i++) {
                        objects[i] = resultSet.getObject(i);
                    }
                    resultSet.close();
                    return objects;
                }
            }
            resultSet.close();
        } catch (Exception ex) {

        }
        return new Object[0];
    }

    public HashMap<String, Object> first(String query, Object... arguments) {
        try {
            PreparedStatement command = arguments == null ? connection().prepareStatement(query) : createCommand(query, arguments);

            ResultSet resultSet;
            HashMap<String, Object> values = new HashMap<>();
            synchronized (actionLock) {
                resultSet = command.executeQuery();
                if (resultSet.next()) {
                    ResultSetMetaData metadata = resultSet.getMetaData();
                    for (int i = 0; i < metadata.getColumnCount(); i++) {
                        values.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                    }
                    return values;
                }
            }
            resultSet.close();
        } catch (Exception ex) {

        }
        return new HashMap<>();
    }

    public List<Object[]> selectRaw(String query, Object... arguments) {
        try {
            PreparedStatement statement = arguments == null ? connection().prepareStatement(query) :
                    createCommand(query, arguments);

            List<Object[]> list = new ArrayList<>();

            synchronized (actionLock) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    ResultSetMetaData metadata = resultSet.getMetaData();
                    Object[] objects =  new Object[metadata.getColumnCount()];
                    for (int i = 0; i < objects.length; i++) {
                        objects[i] = resultSet.getObject(i);
                    }
                    list.add(objects);
                }
            }
            return list;


        } catch (SQLException throwables) {

        }
        return new ArrayList<>();
    }

    public List<HashMap<String, Object>> select(String query, Object... arguments) {
        try {
            PreparedStatement statement = arguments == null ? connection().prepareStatement(query) :
                    createCommand(query, arguments);

            List<HashMap<String, Object>> list = new ArrayList<>();

            synchronized (actionLock) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    ResultSetMetaData metadata = resultSet.getMetaData();
                    HashMap<String, Object> values = new HashMap<>();
                    for (int i = 0; i < metadata.getColumnCount(); i++) {
                        values.put(metadata.getColumnName(i), resultSet.getObject(i));
                    }
                    list.add(values);
                }
            }
            return list;


        } catch (SQLException throwables) {

        }
        return new ArrayList<>();
    }

    public <X> List<X> Select(Class<X> type, String query, Object... arguments) {
        try {
            PreparedStatement statement = arguments == null ? connection().prepareStatement(query) :
                    createCommand(query, arguments);

            List<X> list = new ArrayList<>();

            synchronized (actionLock) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    X instance = type.newInstance();
                    fillObject(resultSet, instance, type);
                    list.add(instance);
                }
            }
            return list;
        } catch (Exception ex) {
            return new ArrayList<>();
        }


    }

    public void fillObject(ResultSet resultSet, Object object, Class<?> clazz) {
        for (Field field : clazz.getFields()) {
            Column column = field.getAnnotation(Column.class);
            try {
                field.set(object, resultSet.getObject(column.name()));
            } catch (IllegalAccessException e) {

            } catch (SQLException throwables) {

            }
        }
    }


    // TODO: TEST THIS CODE
    public boolean dropTable(String tableName) {
        PreparedStatement statement = null;
        try {
            statement = connection().prepareStatement("DROP TABLE ?");
            statement.setString(0, tableName);
            return statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public PreparedStatement createCommand(String query, Object... arguments) {
        StringBuilder realQuery = new StringBuilder();

        int argumentIndex = 0;

        List<Object> values = new ArrayList<>();
        for (char c : query.toCharArray()) {

            if (c == '?') {
                values.add(arguments[argumentIndex++]);

            }

            if (c == '^' ) {
                realQuery.append(arguments[argumentIndex]);
                argumentIndex++;
                continue;
            }

            realQuery.append(c);
        }


        try {
            PreparedStatement cmd = connection().prepareStatement(realQuery.toString());
            if (arguments != null) {
                int index = 1;
                for (Object value : values)
                {
                    cmd.setObject(index++, value);
                }
            }
            return cmd;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean isConnected() {
        try {
            return connected && !connection.isClosed();
        } catch (SQLException throwables) {
            return false;
        }
    }

    public Connection connection() {
        return connection;
    }
}
