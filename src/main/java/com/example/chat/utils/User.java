package com.example.chat.utils;

import java.io.*;
import java.net.Socket;

public class User {
    Socket socket;
    String sendingMessage = "";
    private String[] messages = {"", "", "", ""};

    public User() {
        try {
            socket = new Socket("localhost", 2234);
            new sendMessage().start();
            new getMessage().start();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    class sendMessage extends Thread {
        private void sending() throws IOException {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                if (!sendingMessage.equals("")) {
                    outputStream.writeUTF(sendingMessage);
                    outputStream.flush();
                    if(sendingMessage.equals("exit"))
                        break;
                    sendingMessage = "";
                }
            }
            disconnect();
        }

        @Override
        public void run() {
            try {
                sending();
            } catch (Exception e) {
                System.out.println("Sending error");
            }
        }
    }

    class getMessage extends Thread {
        private void getting() throws IOException, InterruptedException {
            while (true) {
                try {
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    String message = inputStream.readUTF();
                    System.out.println(message);
                    messages[3] = messages[2];
                    messages[2] = messages[1];
                    messages[1] = messages[0];
                    messages[0] = message;

                } catch (Exception e) {
                    messages[3] = messages[2];
                    messages[2] = messages[1];
                    messages[1] = messages[0];
                    messages[0] = "Zostales rozlaczony";
                    break;
                }

                sleep(100);
            }
        }

        @Override
        public void run() {
            try {
                getting();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setSendingMessage(String message) {
        sendingMessage = message;
    }

    public String[] getMessages() {
        return messages;
    }

    public void disconnect() throws IOException {
        socket.close();
    }

}
