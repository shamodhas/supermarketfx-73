package lk.ijse.supermarketfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.supermarketfx.db.DBConnection;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 3/31/2025 9:15 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);

//        new DBConnection();
//        DBConnection.getInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(
                new Scene(
                        new FXMLLoader(getClass().getResource(
                                "/view/LoadingScreen.fxml"
                        )).load()
                )
        );
        primaryStage.show();

        Task<Scene> loadingTask = new Task() {
            @Override
            protected Scene call() throws Exception {
                Parent parent = FXMLLoader.load(
                        getClass().getResource("/view/Dashboard.fxml")
                );
                return new Scene(parent);
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            primaryStage.setTitle("Supermarket");
            primaryStage.setScene(value);
        });

//        primaryStage.setScene();
//        primaryStage.setScene();

        loadingTask.setOnFailed(event -> {
            System.out.println("Fail to load application");
            primaryStage.close();
        });

        new Thread(loadingTask).start();


//        Parent parent = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
//
//        Scene scene = new Scene(parent);
//
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Supermarket");
//        primaryStage.show();
    }
}
