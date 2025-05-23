module lk.ijse.supermarketfx {
    //
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires net.sf.jasperreports.core;
    requires java.mail;

    //
    opens lk.ijse.supermarketfx.controller to javafx.fxml;
    opens lk.ijse.supermarketfx.dto.tm to javafx.base;

    //
    exports lk.ijse.supermarketfx;
}