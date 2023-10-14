package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static BlockingQueue<Connection> connectionQueue;
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;
    private static final String PROPERTIES_FILE = "src/resources/database.properties";


    static {
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        initializePool();
    }

    private static void initializePool() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading properties file.");
        }

        dbUrl = properties.getProperty("db.url");
        dbUser = properties.getProperty("db.user");
        dbPassword = properties.getProperty("db.password");

        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                connectionQueue.offer(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error creating database connections.");
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for a connection.");
        }
    }

    public static void releaseConnection(Connection connection) {
        connectionQueue.offer(connection);
    }
}
