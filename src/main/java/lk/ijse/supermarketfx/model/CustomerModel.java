package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.dto.CustomerDTO;
import lk.ijse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pst = connection.prepareStatement(
//                "select customer_id from customer order by customer_id desc limit 1"
//        );
//        ResultSet resultSet = pst.executeQuery();
        ResultSet resultSet = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");
        char tableCharacter = 'C'; // Use any character Ex:- customer table for C, item table for I
        if (resultSet.next()) {
            String lastId = resultSet.getString(1); // "C001"
            String lastIdNumberString = lastId.substring(1); // "001"
            int lastIdNumber = Integer.parseInt(lastIdNumberString); // 1
            int nextIdNUmber = lastIdNumber + 1; // 2
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber); // "C002"
            return nextIdString;
        }
        // No data recode in table so return initial primary key
        return tableCharacter + "001";
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        // insert into customer values ('C001','Bob','1111111111111','bob@gmail.com','077777777')
//        // insert into customer values (?,?,?,?,?)
//        PreparedStatement pst = connection.prepareStatement("insert into customer values (?,?,?,?,?)");
//
//        // set values for ? marks
//        pst.setString(1, customerDTO.getCustomerId());
//        pst.setString(2, customerDTO.getName());
//        pst.setString(3, customerDTO.getNic());
//        pst.setString(4, customerDTO.getEmail());
//        pst.setString(5, customerDTO.getPhone());

//        int i = pst.executeUpdate();
//        boolean isSaved = i > 0;
//        return isSaved;
        return CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );
    }

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pst = connection.prepareStatement("select * from customer");
        ResultSet resultSet = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDTO> customerDTOArrayList = new ArrayList<>();
        while (resultSet.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            customerDTOArrayList.add(customerDTO);
        }

        return customerDTOArrayList;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
//       search by customerDTO.getCustomerId()
//       update all data
        return CrudUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getCustomerId()
        );
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute(
                "delete from customer where customer_id=?",
                customerId
        );
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    public String findNameById(String selectedCustomerId) throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "select name from customer where customer_id=?",
                selectedCustomerId
        );

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}











