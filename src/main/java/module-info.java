module lk.ijse.supermarketfx {
    //
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    //
    opens lk.ijse.supermarketfx.controller to javafx.fxml;

    //
    exports lk.ijse.supermarketfx;
}