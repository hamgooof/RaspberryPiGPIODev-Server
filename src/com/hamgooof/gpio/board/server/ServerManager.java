package com.hamgooof.gpio.board.server;

import com.hamgooof.helpers.Logger;
import com.hamgooof.gpio.board.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerManager implements Runnable {
    private Main main;
    private ServerSocket _socket;
    private boolean isCancelled = false;

    public ServerManager(Main main, int port) throws IOException {
        _socket = new ServerSocket(port);
        this.main = main;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public void run() {
        String ip = _socket.getLocalSocketAddress().toString();
        ip = ip.substring(ip.indexOf('/') + 1);
        Logger.getLogger().writeln("Listening for connections on " + ip);
        do {
            try {
                Socket socket = _socket.accept();
                if (socket != null) {
                    new Thread(new ServerClient(main, socket)).start();
                }
            } catch (Exception e) {
                Logger.getLogger().writeln("Error accepting socket");
            }
        } while (!isCancelled);
    }

}

class ServerClient implements Runnable {
    private static int SocketCounter = 0;
    private final Socket _socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private int socketNumber;
    private final Main main;

    public ServerClient(Main main, Socket clientSocket) throws IOException {
        _socket = clientSocket;
        inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        socketNumber = SocketCounter++;
        this.main = main;
        log("Connected with IP: " + _socket.getInetAddress().toString());
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(inputStream);

        try {
            do {
                if (scanner.hasNextLine()) {
                    String in = scanner.nextLine();
                    log("Received: " + in);
                    try {
                        process(in);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Thread.sleep(500);
            } while (true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void process(String in) {
        String[] inputs = in.split(",");
        main.parseCommand(inputs);
    }

    private void log(String str) {
        Logger.getLogger().writeln("[" + socketNumber + "]" + str);
    }
}
