module com.example.chat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chat to javafx.fxml;
    opens com.example.chat.controllers to javafx.fxml;
    opens com.example.chat.utils to javafx.fxml;

    exports com.example.chat.controllers;
    exports com.example.chat.utils;
}