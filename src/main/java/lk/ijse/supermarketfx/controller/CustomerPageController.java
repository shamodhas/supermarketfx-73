package lk.ijse.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.supermarketfx.dto.CustomerDTO;
import lk.ijse.supermarketfx.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 3/31/2025 11:18 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class CustomerPageController implements Initializable {
    public Label lblCustomerId;
    public TextField txtName;
    public TextField txtNic;
    public TextField txtEmail;
    public TextField txtPhone;

    // Create a CustomerModel object to access database-related methods (CustomerModel class methods)
    private final CustomerModel customerModel = new CustomerModel();

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        // Data transfer object -dto

        // Create dto object wrap data to single unit
        CustomerDTO customerDTO = new CustomerDTO(
                customerId,
                name,
                nic,
                email,
                phone
        );

        // Call CustomerModel inside saveCustomer method and parse
        // controller to model parse data using dto
        try {
            boolean isSaved = customerModel.saveCustomer(customerDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to save customer.").show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            loadNextId();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws SQLException {
        String nextId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }
}
