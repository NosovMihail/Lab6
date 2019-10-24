package server;

import commands.Command;
import commands.CommandDescriptor;
import commands.CommandsManager;
import commands.FileHandler;
import mainClasses.CollectionManager;
import mumi.Mumi;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentSkipListSet;

public class ClientThread extends Thread {
    private static CommandsManager manager;
    private final CollectionManager collectionManager;
    private ConcurrentSkipListSet<Mumi> mumis;
    private Socket socket;
    private boolean isActive;

    public ClientThread(Socket socket, ConcurrentSkipListSet<Mumi> mumis) {
        collectionManager = new CollectionManager();
        collectionManager.setMumis(mumis);

        isActive = true;

        manager = collectionManager.getCommandsManager();
        manager.addCommand(
                new Command("connect", 0) {
                    @Override
                    public void execute() {
                        manager.println("Connected");
                    }
                    @Override
                    public void describe() {
                        manager.println("Command to check connection.");
                    }
                },
                new Command("load", 1) {
                    @Override
                    public void execute() {
                        try {
                            if (collectionManager.getMumis().addAll(FileHandler.readFile(getArguments())))
                                manager.println("Коллекция изменена");
                            else manager.println("Коллекция не изменилась");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JAXBException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void describe() {
                        manager.println("Дополняет коллекцию элементами из файла с сервера.");
                    }
                },
                new Command("wait", 0) {
                    @Override
                    public void execute() {
                        long start = System.currentTimeMillis();

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        manager.println(Thread.currentThread().getId() + " wait for " + (System.currentTimeMillis() - start));
                    }
                    @Override
                    public void describe() {
                        manager.println("Make a delay.");
                    }
                },
                new Command("import", -1) {
                    @Override
                    public void execute(){
                        try {
                            if (collectionManager.getMumis().addAll(collectionManager.createMumi(getArguments())))
                                manager.println("Коллекция изменена");
                            else manager.println("Коллекция не изменилась");
                        } catch (JAXBException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void describe() {
                        manager.println("Дополняет коллекцию элементами из файла клиента.");
                    }
                },
                new Command("update",0) {
                    @Override
                    public void execute() {
                        manager.println("Collection was updated.");
                    }
                    @Override
                    public void describe() {
                        manager.println("Send collection to the client.");
                    }
                },
                new Command("exit",0) {
                    @Override
                    public void execute() {
                        manager.println("Good bye!");

                    }
                    @Override
                    public void describe() {
                        manager.println("Disconnect user from server.");
                    }
                });

        this.socket = socket;
        this.mumis = mumis;
    }

    public void run() {
        while(isActive) {
            byte[] request = new byte[4096];
            try {
                InputStream ins = socket.getInputStream();
                if (ins.read(request) < 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            answer(request);
        }
    }

    private void answer(byte[] request) {
        CommandDescriptor command;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(request);
             ObjectInputStream ois = new ObjectInputStream(bais);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
             ByteArrayOutputStream bao = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(bao)) {

            command = (CommandDescriptor) ois.readObject();

            manager.setPrintStream(printStream);

            if (command.getNAME().equals("exit")) {
                isActive = false;
            }

            manager.doCommand(command);

            printStream.flush();

            String doings = new String(bao.toByteArray()).trim();

            synchronized (System.out) {
                System.out.println();
                System.out.println("Client's command: " + command.getNAME() + " " + command.getArguments());
                System.out.println();
                System.out.println(doings);
            }

            Response response = new Response(doings, collectionManager.getMumis());

            oos.writeObject(response);
            oos.flush();
            try {
                OutputStream outs = socket.getOutputStream();
                outs.write(baos.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static CommandsManager getManager() {
        return manager;
    }
}