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
    boolean nobodyConnectedYet = true;

    public Server() {
        System.out.println("Utworzono serwer");
        new ListenToUsersConnect().start();
        new ReadUsers().start();
    }

    class ListenToUsersConnect extends Thread {
        private void listen() throws InterruptedException {
            try {
                serverSocket = new ServerSocket(2234);
                while (true) {
                    try {
                        new AddUserToServer(serverSocket.accept());
                        sleep(100);
                    }catch (Exception e){break;}
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
    class AddUserToServer {
        Socket socket;

        public AddUserToServer(Socket socket) {
            this.socket = socket;
            System.out.println("Ktos sie polaczyl");
            users.add(socket);
            System.out.println("USERS: ");
            for (Socket user : users)
                System.out.println(user);
            System.out.println("-----------------");
            u_size++;
        }
    }

    class ReadUsers extends Thread {
        @Override
        public void run() {
            do {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (Socket user : users) {
                    nobodyConnectedYet = false;
                    try {
                        if(!user.isConnected()){
                            users.remove(user);
                            break;
                        }
                        DataInputStream inputStream = new DataInputStream(user.getInputStream());
                        if (inputStream.available() != 0) {
                            String message = inputStream.readUTF();
                            System.out.println(message);
                            for(Socket wuser:users){
                                System.out.println("USER: "+wuser);
                            }
                            boolean disconnect =inputStream.readBoolean();
                            if(disconnect){
                                user.close();
                                users.remove(user);
                                break;
                            }
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
            DisableServer();
        }
    }

    public void RemoveAllUsers() {
        for (Socket userToDisconnect : users) {
            try {
                userToDisconnect.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        nobodyConnectedYet = true;
        users = new ArrayList<>();
    }

    public void DisableServer() {
        RemoveAllUsers();
        try {
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ShowUsers(){
        for(Socket user:users){
            System.out.println(users);
        }
    }
}
