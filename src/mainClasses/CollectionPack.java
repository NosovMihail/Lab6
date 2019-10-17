package mainClasses;

import mumi.Mumi;

import java.util.Vector;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionPack {

    private final Vector<Mumi> mumi = new Vector<>();

    public CollectionPack(){
    }

    public CollectionPack(Vector<Mumi> m){
        mumi.addAll(m);
    }

    public Vector<Mumi> getCollection(){
        return mumi;
    }

}