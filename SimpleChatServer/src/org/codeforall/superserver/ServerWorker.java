package org.codeforall.superserver;

import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable {
    Socket clientSocket;
    Server server;
    BufferedReader in;
    BufferedWriter out;
    private String message = "";
    private String clientUsername;


    public ServerWorker(Server server, Socket clientSocket) throws IOException {
        this.server = server;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.clientSocket = clientSocket;
        this.clientUsername = in.readLine();
    }

    public void reciveSocket() throws IOException {
        message = in.readLine();
        if (message.equalsIgnoreCase("exit")) {
            server.sendAll(clientUsername + " left the chat.", clientUsername);
            clientSocket.close();
        }
    }



    @Override
    public void run(){
        while (clientSocket.isConnected()) {
            try {
                reciveSocket();
                server.sendAll(clientUsername + ": " + message, clientUsername);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getClientUsername(){
        return clientUsername;
    }


}
