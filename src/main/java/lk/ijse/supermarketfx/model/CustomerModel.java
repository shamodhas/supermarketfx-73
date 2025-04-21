package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.db.DBConnection;
import lk.ijse.supermarketfx.dto.CustomerDTO;

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
 * Created: 4/21/2025 10:27 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class CustomerModel {

    public String getNextCustomerId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pst = connection.prepareStatement(
                "select customer_id from customer order by customer_id desc limit 1"
        );

        ResultSet resultSet = pst.executeQuery();

        // C,I
        if (resultSet.next()) {
            // C001
            String lastId = resultSet.getString(1);
            // 001
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            // I
            String nextIdString = String.format("C%03d", nextIdNUmber);
            return nextIdString;
        }
        // I
        return "C001";
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        // insert into customer values ('C001','Bob','1111111111111','bob@gmail.com','077777777')
        // insert into customer values (?,?,?,?,?)
        PreparedStatement pst = connection.prepareStatement("insert into customer values (?,?,?,?,?)");

        // set values for ? marks
        pst.setString(1, customerDTO.getCustomerId());
        pst.setString(2, customerDTO.getName());
        pst.setString(3, customerDTO.getNic());
        pst.setString(4, customerDTO.getEmail());
        pst.setString(5, customerDTO.getPhone());

        int i = pst.executeUpdate();
        boolean isSaved = i > 0;
        return isSaved;
    }

    public void getAllCustomer() {

    }
}
