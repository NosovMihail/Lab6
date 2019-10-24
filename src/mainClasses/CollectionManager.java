package mainClasses;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import commands.FileHandler;
import mumi.Mumi;
import commands.Command;
import commands.CommandsManager;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class CollectionManager {
    private ConcurrentSkipListSet<Mumi> mumis;
    private Gson parser  = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(Expose.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> c) {
            return false;
        }
    })
            .create();
    private CommandsManager commandsManager;
    private long initTime;

    public CollectionManager() {
        initTime = System.currentTimeMillis();
        mumis = new ConcurrentSkipListSet<>();

        commandsManager = new CommandsManager();
        commandsManager.addCommand(//Добавление необходимых команд
                new Command("add", 1) {
                    @Override
                    public void execute() {
                        if (mumis.add(parser.fromJson(getArguments(), Mumi.class))) {
                            commandsManager.println("Successfuly added.");
                        } else {
                            commandsManager.println("Adding is failed!!!");
                        }
                    }
                    @Override
                    public void describe() {
                        commandsManager.println("Add element to the collection.");
                    }
                },
                new Command("remove_lowerest", 1) {
                    @Override
                    public void execute(){
                        Mumi Mumi = parseElement(getArguments());
                        mumis.removeIf(obj -> {
                            if (obj.compareTo(Mumi) <= 0) {
                                commandsManager.println(obj + " deleted");
                                return true;
                            }
                            commandsManager.println(obj + " not deleted");
                            return false;
                        });
                    }
                    @Override
                    public void describe() {
                        commandsManager.println("Delete all elements that lower than parameter.");
                    }
                },
                new Command("show", 0) {
                    @Override
                    public void execute() {
                        mumis.forEach(commandsManager::println);
                        if(mumis.size() == 0) {
                            commandsManager.println("Collection is empty.");
                        }
                    }
                    @Override
                    public void describe() {
                        commandsManager.println("Displays the contents of the ConcurrentSkipListSet.");
                    }
                },
                new Command("save", 0) {
                    @Override
                    public void execute() {
                        FileHandler.writeFile(mumis);
                    }
                    @Override
                    public void describe() {
                        commandsManager.println("Сохраняет коллекцию в файл save.xml.");
                    }
                },
                new Command("clear", 0) {
                    @Override
                    public void execute() {
                        mumis.clear();
                    }
                    @Override
                    public void describe() {
                        commandsManager.println("Clear the collection.");
                    }
                },
                new Command("info", 0) {
                    @Override
                    public void execute() {
                        commandsManager.println("\nMumi collections");
                        commandsManager.println("Collection type: " + mumis.getClass().getName());
                        commandsManager.println("Elements type: " + Mumi.class.getName());
                        commandsManager.println("Count of elements: " + mumis.size());
                        commandsManager.println("Initialization date: " + new Date(initTime) + '\n');
                    }
                    @Override
                    public void describe() {
                        commandsManager.println("Show info about collection.");
                    }
                },
                new Command("remove", 1) {
                    @Override
                    public void execute() {
                        if(mumis.remove(parseElement(getArguments()))) {
                            commandsManager.println("Successfuly deleted.");
                        } else {
                            commandsManager.println("Deleting is failed!!!");
                        }
                    }
                    @Override
                    public void describe() {
                        commandsManager.println("Delete element from the collection.");
                    }
                }
        );
    }

    public ConcurrentSkipListSet<Mumi> getMumis() {
        return mumis;
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public void setCommandsManager(CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
    }

    public synchronized void setMumis(ConcurrentSkipListSet<Mumi> mumis) {
        this.mumis = mumis;
        initTime = System.currentTimeMillis();
    }

    private Mumi parseElement(String dataStr) {
        return parser.fromJson(dataStr, Mumi.class);
    }

    public ConcurrentSkipListSet<Mumi> createMumi(String arguments) throws JAXBException {

        return JAXB.unmarshal(new StringReader(arguments.substring(0, arguments.lastIndexOf(">")+1)), CollectionPack.class).getCollection();
    }

/*   public synchronized ConcurrentSkipListSet<Mumi> createMumi(String arguments) {
        System.err.println(arguments);
        ConcurrentSkipListSet<Mumi> ConcurrentSkipListSet = new ConcurrentSkipListSet<>();
        JSONArray dataJSON = null;
        try {
            System.err.println(arguments);
            dataJSON = (JSONArray) parser.parse(arguments.trim());
        } catch (Exception e) {
            e.printStackTrace();
            commandsManager.println("Data in file is not a correct JSON. Check the data in file.");
        }

        commandsManager.println("Data converting from JSON started.");
        dataJSON.forEach(obj -> ConcurrentSkipListSet.add(new Mumi((JSONObject) obj)));
        commandsManager.println("Data converting finished");
        return ConcurrentSkipListSet;
    }*/
}