package no.ntnu.iie.stud.cateringstorm;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import no.ntnu.iie.stud.cateringstorm.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = Database.getConnection()) {
            try(PreparedStatement statement = (PreparedStatement)connection.prepareStatement("SELECT forename FROM employee")) {
                try(ResultSet resultSet = statement.executeQuery()) {
                    while(resultSet.next()) {
                        System.out.println(resultSet.getString("forename"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
