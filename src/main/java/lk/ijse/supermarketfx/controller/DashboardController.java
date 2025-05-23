package lk.ijse.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 3/31/2025 9:29 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class DashboardController implements Initializable {

    public AnchorPane ancMainContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // this method run with Dashboard.fxml ui load
       navigateTo("/view/CustomerPage.fxml");
    }

    public void btnGoCustomerPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/CustomerPage.fxml");
    }

    public void btnGoItemPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/ItemPage.fxml");
    }

    public void btnGoOrderPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/OrderPage.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancMainContainer.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());

            ancMainContainer.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    public void btnSendMailPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/SendMailPage.fxml");
    }
}
