package com.epam.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Sergey_Stefoglo on 7/21/2016.
 */
public class ConnectionPool {
    public Connection con = null;
    private static volatile ConnectionPool instance;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class){
                if (instance == null) {
                instance = new ConnectionPool();
                }
            }
        }

        return instance;
    }

    private static final String DB_URL = "oracle.jdbc.driver.OracleDriver";

    public Connection getConnection() {
        try {
            Class.forName(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResourceBundle labels = ResourceBundle.getBundle("config");
            con = DriverManager.getConnection(labels.getString("database"),
                    labels.getString("user"), labels.getString("password"));


        } catch (Exception e) {
            e.printStackTrace();

        }

        return con;
    }

    public void removeConnection() throws SQLException {
        con.close();
    }
}


