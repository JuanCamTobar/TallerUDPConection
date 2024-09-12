package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PeerController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        showWindow("peerController-view",stage);
    }

    public static void showWindow(String fxml, Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(PeerController.class.getResource(fxml+".fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 400, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(stage==null) stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PeerController");
        stage.show();
    }
}
