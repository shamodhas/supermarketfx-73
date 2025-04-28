package lk.ijse.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.supermarketfx.dto.CartDTO;
import lk.ijse.supermarketfx.dto.ItemDTO;
import lk.ijse.supermarketfx.dto.OrderDTO;
import lk.ijse.supermarketfx.dto.tm.CartTM;
import lk.ijse.supermarketfx.model.CustomerModel;
import lk.ijse.supermarketfx.model.ItemModel;
import lk.ijse.supermarketfx.model.OrderModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 3/31/2025 11:19 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class OrderPageController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTM, String> colItemId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Double> colPrice;

    @FXML
    private TableColumn<CartTM, Integer> colQuantity;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemPrice;

    @FXML
    private Label lblItemQty;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private TextField txtAddToCartQty;

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();

    private final ObservableList<CartTM> cartData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValues();

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        tblCart.setItems(cartData);
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(orderModel.getNextOrderId());

//        orderDate.setText(String.valueOf(LocalDate.now()));
        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemIds();
    }

    private void loadItemIds() throws SQLException {
        ArrayList<String> itemIdsList = itemModel.getAllItemIds();
        ObservableList<String> itemIds = FXCollections.observableArrayList();
        itemIds.addAll(itemIdsList);
        cmbItemId.setItems(itemIds);
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIdsList = customerModel.getAllCustomerIds();
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        customerIds.addAll(customerIdsList);
        cmbCustomerId.setItems(customerIds);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        String cartQtyString = txtAddToCartQty.getText();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.WARNING, "Please select item..!").show();
            return;
        }

        if (!cartQtyString.matches("^[0-9]+$")) {
            new Alert(Alert.AlertType.WARNING, "Please enter valid quantity..!").show();
            return;
        }

        String itemQtyOnStockString = lblItemQty.getText();

        int cartQty = Integer.parseInt(cartQtyString);
        int itemQtyOnStock = Integer.parseInt(itemQtyOnStockString);

        // 10 < 15
        if (itemQtyOnStock < cartQty) {
            new Alert(Alert.AlertType.WARNING, "Not enough item quantity..!").show();
            return;
        }

        String itemName = lblItemName.getText();
        String itemUnitPriceString = lblItemPrice.getText();

        double itemUnitPrice = Double.parseDouble(itemUnitPriceString);
        double total = itemUnitPrice * cartQty;


        for (CartTM cartTM : cartData) {
            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQty() + cartQty;

                if (itemQtyOnStock < newQty) {
                    new Alert(Alert.AlertType.WARNING, "Not enough item quantity..!").show();
                    return;
                }
                txtAddToCartQty.setText("");
                cartTM.setCartQty(newQty);
                cartTM.setTotal(newQty * itemUnitPrice);

                tblCart.refresh();
                return;
            }
        }

        Button removeBtn = new Button("Remove");
        CartTM cartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                itemUnitPrice,
                total,
                removeBtn
        );

        removeBtn.setOnAction(action -> {
            cartData.remove(cartTM);

            tblCart.refresh();
        });

        txtAddToCartQty.setText("");
        cartData.add(cartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please add items to cart..!").show();
            return;
        }

//        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        String selectedCustomerId = cmbCustomerId.getValue();

//        if (selectedCustomerId == null){
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());

        // CartDTO - OrderDetailsDTO
        ArrayList<CartDTO> cartList = new ArrayList<>();

        for (CartTM cartTM : cartData) {
            CartDTO cartDTO = new CartDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQty(),
                    cartTM.getUnitPrice()
            );
            cartList.add(cartDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                selectedCustomerId,
                dateOfOrder,
                cartList
        );

//        cartData
//        orderDetail -> {orderId, itemId, qty, price}
        try {
            boolean isSaved = orderModel.placeOrder(orderDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Order saved").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Order fail..!").show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }


    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        String selectedCustomerName = customerModel.findNameById(selectedCustomerId);

        if (selectedCustomerName != null) {
            lblCustomerName.setText(selectedCustomerName);
        } else {
            lblCustomerName.setText("");
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO itemDTO = itemModel.findById(selectedItemId);

        if (itemDTO != null) {
            lblItemName.setText(itemDTO.getName());
            lblItemQty.setText(String.valueOf(itemDTO.getQuantity()));
            lblItemPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        } else {
            lblItemName.setText("");
            lblItemQty.setText("");
            lblItemPrice.setText("");
        }
    }

}
