package lk.ijse.supermarketfx.util;

import lk.ijse.supermarketfx.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 4/21/2025 3:43 PM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

// This class contains utility methods for executing CRUD operations (Create, Read, Update, Delete) with the database.
public class CrudUtil {

    // A generic method to execute SQL queries, with a flexible return type `T`.
    // It accepts an SQL statement and a variable number of parameters (obj) to insert into SQL query.
    public static <T> T execute(String sql, Object... obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        // Iterates through the variable arguments (obj) and sets them in the prepared statement in place of the placeholders (?) in the SQL query.
        // The loop starts from 0, but `setObject` expects positions starting from 1, hence (i + 1).
        for (int i = 0; i < obj.length; i++) {
            pst.setObject(i + 1, obj[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            // If the SQL query is a SELECT statement, the prepared statement is executed as a query.

            // A ResultSet is returned, which contains the data retrieved from the database.
            ResultSet resultSet = pst.executeQuery();

            // The result set is returned, cast to the generic type `T`.
            return (T) resultSet;
        } else {
            // For non-SELECT queries (INSERT, UPDATE, DELETE), executeUpdate() is called.

            // This method returns the number of rows affected by the query.
            int i = pst.executeUpdate();

            // If one or more rows are affected, the operation is considered successful (`isSuccess = true`).
            boolean isSuccess = i > 0;

            // Returns the result of the operation (true if successful, false otherwise), cast to the generic type `T`.
            return (T) (Boolean) isSuccess;
        }
    }
}
