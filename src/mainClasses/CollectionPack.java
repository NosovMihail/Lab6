package mainClasses;

import mumi.Mumi;

import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionPack {

    private final TreeSet<Mumi> mumi = new TreeSet<>();

    public CollectionPack(){
    }

    public CollectionPack(TreeSet<Mumi> m){
        mumi.addAll(m);
    }

    public TreeSet<Mumi> getCollection(){
        return mumi;
    }

}