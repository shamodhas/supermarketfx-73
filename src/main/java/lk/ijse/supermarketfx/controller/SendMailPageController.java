package lk.ijse.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 5/23/2025 12:18 PM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class SendMailPageController {

    @FXML
    private TextArea txtMessage;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTo;

    @Setter
    private String customerEmail;

//    public void hello() {
//    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
//        String toMail = txtTo.getText();
        System.out.println(customerEmail);
        String toMail = customerEmail;
        String subject = txtSubject.getText();
        String message = txtMessage.getText();

        if (toMail == null || subject == null || message == null) {
            return;
        }

        String from = "shamodha7@gmail.com";
        String password = "rzte ajuj jbxs esmh";

        // Hold configuration settings
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            // override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        try {
            Message mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            new Alert(Alert.AlertType.INFORMATION, "Mail send successfully..!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to send Mail..!").show();
            e.printStackTrace();
        }
    }
}

