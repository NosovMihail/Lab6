package server;

import commands.FileHandler;
import mainClasses.CollectionManager;
import mumi.Mumi;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    private Vector<Mumi> mumis;
    private ServerSocket serverSocket;
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port, 0, InetAddress.getLocalHost());
        System.out.println(serverSocket.getInetAddress());
        CollectionManager manager = new CollectionManager();
        try {
            if (manager.getMumis().addAll(FileHandler.readFile("save.xml"))) {
                System.out.println("Collection changed.");
            } else {
                System.out.println("Collection didn't changed.");
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        mumis = manager.getMumis();

        System.out.println("Server started");
        System.out.println("IP: " + serverSocket.getLocalSocketAddress());
    }

    private void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            ClientThread clientThread = new ClientThread(socket, mumis);
            clientThread.start();
            //clientThread.run();
        }
    }

    public static void main(String[] args) {
        int port = 1234;

        //By arguments
        if (args.length > 0) {
            port = Integer.valueOf(args[0]);
        } else {
            //By input stream
            Scanner scanner = new Scanner(System.in);
            System.out.print("Port: ");
            port = scanner.nextInt();
        }

        try {
            Server server = new Server(port);
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
