package commands;

import mainClasses.Caretaker;
import mainClasses.CollectionPack;
import mainClasses.Originator;
import mumi.Mumi;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Vector;

public class FileHandler {

/*    public static Vector<Mumi> readFile(String fileName) {
        Vector<Mumi> vector = new Vector<>();

        char[] fileContent = {};

        try {
            File inputFile = new File(fileName);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(inputFile));
            fileContent = new char[(int) inputFile.length()];
            isr.read(fileContent);
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dataSTR = new String(fileContent);
        JSONArray dataJSON = null;
        try {
            dataJSON = (JSONArray) parser.parse(dataSTR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataJSON.forEach(obj -> vector.add(new Mumi((JSONObject) obj)));

        return vector;
    }*/

    public static Vector<Mumi> readFile(String fileName) throws IOException, SecurityException, JAXBException {
        Caretaker caretaker = new Caretaker();
        caretaker.setFile(new File(fileName));
        Originator originator = new Originator();
        File collectionFile = caretaker.getFile();
        if (!collectionFile.canRead() || !collectionFile.canWrite()) throw new SecurityException("Нет прав на чтение/запись файла.");
        try {
            originator.restoreState(collectionFile);
        }catch (JAXBException e){
            throw new JAXBException("Ошибка синтаксиса файла.");
        }
        System.out.println("Коллекция успешно загружена.");
        return originator.getState().getCollection();
    }

/*    public static void writeFile(Vector<Mumi> Mumis) {
        JSONArray JSONdata = new JSONArray();
        for (Mumi Mumi : Mumis) {
            try {
                JSONdata.add(Mumi.toJSON());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            bos.write(JSONdata.toJSONString().getBytes());
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void writeFile(Vector<Mumi> mumis) {
        try{
            Caretaker caretaker = new Caretaker();
            caretaker.setFile(new File("save.xml"));
            Originator originator = new Originator();
            CollectionPack collectionPack = new CollectionPack(mumis);
            originator.setState(collectionPack);
            caretaker.setFile(originator.saveState(caretaker.getFile(), collectionPack));
        } catch (JAXBException | IOException ex) {
            System.out.println("Не подлежит сохранению.");
            ex.printStackTrace();
        }
    }
}