package mainClasses;

import mumi.Mumi;

import java.util.concurrent.ConcurrentSkipListSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionPack {

    private final ConcurrentSkipListSet<Mumi> mumi = new ConcurrentSkipListSet<>();

    public CollectionPack(){
    }

    public CollectionPack(ConcurrentSkipListSet<Mumi> m){
        mumi.addAll(m);
    }

    public ConcurrentSkipListSet<Mumi> getCollection(){
        return mumi;
    }

}