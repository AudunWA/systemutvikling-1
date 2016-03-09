package no.ntnu.iie.stud.cateringstorm.database;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Audun on 09.03.2016.
 */
public class DatabaseConnection {
    private Connection mysqlConnection;

    public DatabaseConnection(String connectionString) throws SQLException {
        this.mysqlConnection = (Connection)DriverManager.getConnection(connectionString);

        try(Connection conn = (Connection)DriverManager.getConnection(connectionString)) {

        }
    }
}
