package lk.ijse.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.supermarketfx.dto.CustomerDTO;
import lk.ijse.supermarketfx.dto.tm.CustomerTM;
import lk.ijse.supermarketfx.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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

    // TM - table model Ex: CustomerTM
    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM, String> colId;
    public TableColumn<CustomerTM, String> colName;
    public TableColumn<CustomerTM, String> colNic;
    public TableColumn<CustomerTM, String> colEmail;
    public TableColumn<CustomerTM, String> colPhone;

    // Create a CustomerModel object to access database-related methods (CustomerModel class methods)
    private final CustomerModel customerModel = new CustomerModel();

    public Button btnReport;
    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // table column and tm class properties link
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
//            loadTableData();
//            loadNextId();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadTableData() throws SQLException {
//        1.
//        ArrayList<CustomerDTO> customerDTOArrayList = customerModel.getAllCustomer();
//        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

//        for (CustomerDTO customerDTO : customerDTOArrayList) {
//            CustomerTM customerTM = new CustomerTM(
//                    customerDTO.getCustomerId(),
//                    customerDTO.getName(),
//                    customerDTO.getNic(),
//                    customerDTO.getEmail(),
//                    customerDTO.getPhone()
//            );
//            customerTMS.add(customerTM);
//        }
//        tblCustomer.setItems(customerTMS);

//        2. Full short code (Single line)
        tblCustomer.setItems(FXCollections.observableArrayList(
                customerModel.getAllCustomer().stream()
                        .map(customerDTO -> new CustomerTM(
                                customerDTO.getCustomerId(),
                                customerDTO.getName(),
                                customerDTO.getNic(),
                                customerDTO.getEmail(),
                                customerDTO.getPhone()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            // save button (id) -> enable
            btnSave.setDisable(false);

            // update, delete button (id) -> disable
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtName.setText("");
            txtNic.setText("");
            txtEmail.setText("");
            txtPhone.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

//        if (!isValidName) return;

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        if (!isValidNic) txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
        if (!isValidEmail) txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        if (!isValidPhone) txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");


//        [A-Za-z ]+
//        1. Using Pattern object java.util.regex
//        Pattern namePattern = Pattern.compile("^[A-Za-z ]+$");
//        boolean isValidName = namePattern.matcher(name).matches();
//        System.out.println(name + " is valid: " + isValidName);

//        2. Using String class matches() method
//        boolean isValidName = name.matches("^[A-Za-z ]+$");
//        System.out.println(name + " is valid: " + isValidName);


        // Data transfer object -dto

        // Create dto object wrap data to single unit
        CustomerDTO customerDTO = new CustomerDTO(
                customerId,
                name,
                nic,
                email,
                phone
        );

        // Call CustomerModel inside saveCustomer
        // method and parse

        // controller to model parse data using dto
        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            try {
                boolean isSaved = customerModel.saveCustomer(customerDTO);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save customer.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to save customer.").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        CustomerDTO customerDTO = new CustomerDTO(
                customerId,
                name,
                nic,
                email,
                phone
        );

        try {
            boolean isUpdated = customerModel.updateCustomer(customerDTO);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to update customer.").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String customerId = lblCustomerId.getText();
            try {
                boolean isDeleted = customerModel.deleteCustomer(customerId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete customer.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer.").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        // page set to initial look
        resetPage();
    }

    private void loadNextId() throws SQLException {
        String nextId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomerId());
            txtName.setText(selectedItem.getName());
            txtNic.setText(selectedItem.getNic());
            txtEmail.setText(selectedItem.getEmail());
            txtPhone.setText(selectedItem.getPhone());

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

}
