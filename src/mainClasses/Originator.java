package mainClasses;

import javax.xml.bind.*;
import java.io.*;

public class Originator {

    private CollectionPack state;

    public void setState(CollectionPack state) {
        this.state = state;
    }

    public CollectionPack getState() {
        return state;
    }

    public File saveState(File collectionFile, CollectionPack collection) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(CollectionPack.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        BufferedOutputStream writer = null;
        try {
            writer = new BufferedOutputStream(new FileOutputStream(collectionFile));
        } catch (IOException e) {
            collectionFile.createNewFile();
        }
        marshaller.marshal(collection, writer);
        return collectionFile;
    }

    public void restoreState(File collection) throws JAXBException, IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(collection));
        this.state = JAXB.unmarshal(reader, CollectionPack.class);
    }
}