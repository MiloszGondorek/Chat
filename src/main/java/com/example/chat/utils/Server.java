package com.example.chat.utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    ServerSocket serverSocket;
    List<Socket> users = new ArrayList<>();
    int u_size = 0;

    public Server() {
        System.out.println("Utworzono serwer");
        new ListenToUsers().start();
        new ReadUsers().start();
    }

    class ListenToUsers extends Thread {
        private void listen() throws InterruptedException {
            try {
                serverSocket = new ServerSocket(2234);
                while (true) {
                    new ServerThread(serverSocket.accept()).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                listen();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class ServerThread extends Thread {
        Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
            System.out.println("Ktos sie polaczyl");
            users.add(socket);
            System.out.println("USERS: ");
            for (Socket user : users)
                System.out.println(user);
            System.out.println("-----------------");
            u_size++;
        }

        void server() throws IOException, InterruptedException {
        }

        @Override
        public void run() {
            try {
                server();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class ReadUsers extends Thread {
        boolean nobodyConnectedYet = true;

        @Override
        public void run() {
            do {
                try {
                    sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (Socket user : users) {
                    nobodyConnectedYet = false;
                    try {
                        DataInputStream inputStream = new DataInputStream(user.getInputStream());
                        if (inputStream.available() != 0) {
                            String message = inputStream.readUTF();
                            if (!message.equals("")) {
                                for (Socket userToSend : users) {
                                    DataOutputStream outputStream = new DataOutputStream(userToSend.getOutputStream());
                                    outputStream.writeUTF(message);
                                }
                                if (message.equals("exit")) {
                                    user.close();
                                    users.remove(user);
                                    break;
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } while (nobodyConnectedYet || users.size() > 0);
            System.out.println("Wylaczam server");
        }
    }
}
