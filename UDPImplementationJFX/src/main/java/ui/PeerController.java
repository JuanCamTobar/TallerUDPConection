package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PeerController extends Application implements Initializable{

    @FXML
    private TextField ipDest;

    @FXML
    private TextField puertoDes;

    @FXML
    private TextField puertoLoc;

    @FXML
    private ChoiceBox<String> peerChoiceBox;

    @FXML
    private Button botonSiguiente;

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

    public static void hideWindow(Stage stage){
        stage.hide();
    }

    public void connect(ActionEvent actionEvent) {
        String peer = peerChoiceBox.getValue();
        String ip = ipDest.getText();

        boolean flag = true;

        while (flag) {
            if (!ip.isEmpty() && !puertoDes.getText().isEmpty() && !puertoLoc.getText().isEmpty()) {
                int pueDes = Integer.parseInt(puertoDes.getText());
                int pueLoc = Integer.parseInt(puertoLoc.getText());
                if (peer.equals("Peer A")) {
                    PeerA peerA = new PeerA();
                    hideWindow((Stage) botonSiguiente.getScene().getWindow());
                    peerA(ip,pueDes);
                    peerA.connectionPeerA(pueLoc);
                    flag = false;
                } else if (peer.equals("Peer B")) {
                    PeerB peerB = new PeerB();
                    hideWindow((Stage) botonSiguiente.getScene().getWindow());
                    peerB(ip,pueDes);
                    peerB.connectionPeerB(pueLoc);
                    flag = false;
                } else {
                    System.out.println("Por favor ingrese la información necesaria.");
                    flag=false;
                }
            }else{
                System.out.println("Por favor ingrese la información necesaria.");
                flag=false;
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        peerChoiceBox.getItems().addAll("Peer A", "Peer B");
        peerChoiceBox.setValue("");
    }

    public void peerA(String ip, int pueDes){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("peerA-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 400, 400);

            PeerA peerAController = loader.getController();
            peerAController.setConnectionData(ip, String.valueOf(pueDes));

            stage.setScene(scene);
            stage.setTitle("Peer A");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void peerB(String ip, int pueDes) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("peerB-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 400, 400);

            PeerB peerBController = loader.getController();
            peerBController.setConnectionData(ip, String.valueOf(pueDes));

            stage.setScene(scene);
            stage.setTitle("Peer B");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
