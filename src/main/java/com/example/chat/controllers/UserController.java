package com.example.chat.controllers;

import com.example.chat.utils.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserController {

    @FXML
    public TextField userInputMessage;
    @FXML
    public Label Message1, Message2, Message3, Message4;
    private final User user;

    public UserController() {
        user = new User();
        new gettingMessage().start();
    }

    public void send(ActionEvent actionEvent) {
        user.setSendingMessage(userInputMessage.getText());
        userInputMessage.setText("");
    }

    private class gettingMessage extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    String[] messages = user.getMessages();
                    if (Message1 != null) {
                        Platform.runLater(() -> {
                            Message1.setText(messages[3]);
                            Message2.setText(messages[2]);
                            Message3.setText(messages[1]);
                            Message4.setText(messages[0]);
                        });
                        sleep(300);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}
