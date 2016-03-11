package no.ntnu.iie.stud.cateringstorm.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Helper class to use database connections. Use Database.getConnection() to get a new database connection.
 * Created by Audun on 10.03.2016.
 */
public final class Database {
    private static final MysqlDataSource dataSource = new MysqlDataSource();

    static {
        dataSource.setUrl("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/g_tdat1006_t6");
        dataSource.setUser("g_tdat1006_t6");
        dataSource.setPassword("1YdfqApg");
    }

    /***
     *
     * @return A open connection to the database
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
