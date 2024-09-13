package ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.util.UDPConnection;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PeerA implements Initializable {

    public static final Scanner sc = new Scanner(System.in);

    private String ip;

    private String pueDes;

    private boolean running=true;

    @FXML
    private Label ipField;

    @FXML
    private Label puertoDes;

    @FXML
    private TextField messageField;

    @FXML
    private TextArea receivedMessagesArea;

    @FXML
    private Button cerrar;

    public void connectionPeerA(int pueLoc) {
        UDPConnection connection = UDPConnection.getInstance();
        // puerto de escucha | recepción
        connection.setPort(pueLoc);


        connection.start();
    }

    public void setConnectionData(String ip, String pueDes) {
        this.ip = ip;
        this.pueDes = pueDes;

        if (ipField != null && puertoDes != null) {
            ipField.setText(ip);
            puertoDes.setText(pueDes);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        receivedMessagesArea.setText("");
        ipField.setText(this.ip);
        puertoDes.setText(this.pueDes);
        messageField.setText("");
        checkForNewMessages(UDPConnection.getInstance());

    }

    public void enviarMensaje(ActionEvent actionEvent) {
        UDPConnection connection = UDPConnection.getInstance();
        String mensaje=messageField.getText();
        connection.sendDatagram(mensaje, ip, Integer.parseInt(pueDes));
        messageField.setText("");
    }

    public void checkForNewMessages(UDPConnection connection) {
        new Thread(() -> {
            while (running) {
                String lastMessage = connection.getLastMessage();
                if (lastMessage != null) {
                    Platform.runLater(() -> {
                        receivedMessagesArea.setText(lastMessage);
                    });
                } else {
                    Platform.runLater(() -> {
                        receivedMessagesArea.setText("");
                    });
                }
                try {
                    Thread.sleep(500); // Añadir un pequeño retardo para evitar consumo excesivo de CPU
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void terminarEjecucion(ActionEvent actionEvent) {
        running=false;
        UDPConnection.getInstance().setRunning(false);
        PeerController.hideWindow((Stage) cerrar.getScene().getWindow());
        UDPConnection.getInstance().interrupt();
    }

}