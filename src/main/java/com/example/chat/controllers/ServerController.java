package com.example.chat.controllers;

import com.example.chat.utils.ChangeScene;
import com.example.chat.utils.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerController {
    @FXML
    public Button disconnect;
    Server server;

    public ServerController() {
        server = new Server();
    }

    public void DisconnectAllUsers(ActionEvent actionEvent) {
        server.RemoveAllUsers();
    }

    public void DisableServer(ActionEvent actionEvent) throws IOException {
        server.DisableServer();
        new ChangeScene((Stage) disconnect.getScene().getWindow(),0);
    }
}
