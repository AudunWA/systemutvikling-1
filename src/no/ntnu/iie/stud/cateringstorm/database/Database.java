package no.ntnu.iie.stud.cateringstorm.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper class to use database connections. Use Database.getConnection() to get a new database connection.
 * Created by Audun on 10.03.2016.
 */
public final class Database {
    private static final MysqlDataSource dataSource = new MysqlDataSource();

    static {
        dataSource.setUrl(GlobalStorage.getProperties().getProperty("mysql.url"));
        dataSource.setUser(GlobalStorage.getProperties().getProperty("mysql.user"));
        dataSource.setPassword(GlobalStorage.getProperties().getProperty("mysql.password"));
    }

    /***
     * @return A open connection to the database
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Gets the generated key (usually AUTO_INCREMENT id) from a Statement.
     *
     * @param statement The active statement
     * @return The generated key, or -1 if error.
     */
    public static int getGeneratedKeys(Statement statement) {
        int generatedKey = -1;
        try (ResultSet result = statement.getGeneratedKeys()) {
            if (result.next()) {
                generatedKey = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedKey;
    }
}
