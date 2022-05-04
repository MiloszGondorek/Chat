package com.example.chat.controllers;

import com.example.chat.utils.ChangeScene;
import com.example.chat.utils.Server;
import com.example.chat.utils.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MenuController {

    @FXML
    private Button server_button;

    public void startServer(ActionEvent actionEvent) throws IOException {
        new ChangeScene((Stage) server_button.getScene().getWindow(), 1);
    }

    public void joinServer(ActionEvent actionEvent) throws IOException {
        new ChangeScene((Stage) server_button.getScene().getWindow(), 2);
    }
}
