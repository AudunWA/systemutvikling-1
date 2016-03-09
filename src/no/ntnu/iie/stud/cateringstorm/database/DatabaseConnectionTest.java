package no.ntnu.iie.stud.cateringstorm.database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Audun on 09.03.2016.
 */
public class DatabaseConnectionTest {
    @Test
    public void testConnection() {
        try (Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/g_tdat1006_t6", "g_tdat1006_t6", "1YdfqApg")) {
try(Statement statement = (Statement)connection.prepareStatement("SELECT * FROM g_tdat1006_t6.test")) {

}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}