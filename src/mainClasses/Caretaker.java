package mainClasses;

import java.io.*;

public class Caretaker {

    private File collection = null;

    public void setFile(File file){
        this.collection = file;
    }

    public File getFile(){
        return this.collection;
    }
}
