package mumi;

import com.google.gson.annotations.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;

@XmlType(name = "mumi", propOrder = {
        "name",
        "myArea",
        "gender",
        "position",
        "condition",
})
public class Mumi implements Serializable, Actionable, Showable, Comparable<Mumi> {

    @XmlElement(required = true)
    private String name;
    //@Expose
    @XmlElement
    private Area myArea;
    @XmlElement
    private Gender gender;
    private Position position;
    private Condition condition;

    public Mumi(){this.name = null;
        this.gender = null;
        position = Position.STAND;
        condition = Condition.NORMAL;}

    public Mumi(String name, Gender gender){
        if(name == null){
            throw new NullLine("Empty name");
        }
        this.name = name;
        this.gender = gender;
        position = Position.STAND;
        condition = Condition.NORMAL;
    }

    public Gender getGender(){
        return this.gender;
    }

    //@Override
    /*public void goLookingFor(Area area, Thing thing, boolean showDescription){
        System.out.printf("%1$s отправляется искать %2$s. \n", this.getName(), thing.getName());
        if(showDescription) {
            System.out.println(LookingFor.discription);
        }
        moveToArea(area);
    }*/

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void lieNear(Thing thing){
        setPosition(Position.LIE);
        if(this.getGender() == Gender.FEMALE) {
            System.out.printf("%1$s прилегла у %2$s. \n", this.getName(), thing.getName());
        }else if(this.getGender() == Gender.MALE){
            System.out.printf("%1$s прилёг у %2$s. \n", this.getName(), thing.getName());
        } else {
            System.out.printf("%1$s прилегло у %2$s. \n", this.getName(), thing.getName());
        }
    }

    /*public void lookAround(){
        System.out.printf("%1$s огляделся. \n", this.getName());
        lookTo(AreaPart.CENTER);
        lookTo(AreaPart.NORTH);
        lookTo(AreaPart.WEST);
        lookTo(AreaPart.SOUTH);
        lookTo(AreaPart.EAST);
    }*/

    /*public void lookTo(AreaPart part){
        ArrayList<Object> obj = getMyArea().getObjects(part);
        for(Object ob: obj) {
            String name = null;
            if ((ob.getClass() == Creature.class)||(ob.getClass().getSuperclass() == Creature.class)) {
                if(ob != this) {
                    name = ((Creature) ob).getName();
                }
            }
            if (((ob.getClass() == Thing.class)||(ob.getClass().getSuperclass() == Thing.class))&&(((Thing)ob).isVisible())) {
                name = ((Thing) ob).getName();
            }
            if ((ob.getClass() == Area.class)||(ob.getClass().getSuperclass() == Area.class)) {
                name = ((Area) ob).getName();
            }
            if (name != null) {
                System.out.printf("%1$s видит на %3$s %2$s. \n", this.getName(), name, AreaPart.getName(part));
            }
        }
    }

    public void see() {
        if(this.getPosition() == Position.LIE){
            String seeing = "%1$s видит ";
            for(int i = 0; i < getMyArea().getObjects(AreaPart.CENTER).size(); i++){
                if(this.getMyArea().getObjects(AreaPart.CENTER).get(i).getClass() == Plant.class){
                    Plant plant = (Plant) this.getMyArea().getObjects(AreaPart.CENTER).get(i);
                    if((plant.getSize() == Size.NORMAL)||(plant.getSize() == Size.BIG)){
                        seeing += plant.getName() + ", ";
                    }
                }
            }
            System.out.printf(seeing + "голубое небо. \n", this.getName());
        }
    }*/

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Condition getCondition() {
        return condition;
    }

    public void sleep(){
        this.setCondition(Condition.SLEEP);
        System.out.printf("%1$s засыпает. \n", this.getName());
    }

    public void think(String th){
        if(this.getGender() == Gender.MALE) {
            System.out.printf("\"%1$s\" - подумал %2$s. \n", th, this.getName());
        }else if(this.getGender() == Gender.FEMALE){
            System.out.printf("\"%1$s\" - подумала %2$s. \n", th, this.getName());
        }else {
            System.out.printf("\"%1$s\" - подумало %2$s. \n", th, this.getName());
        }
    }

    //@Override
    /*public void moveToArea(Area area) {
        getMyArea().removeObject(this);
        if(this.getGender() == Gender.MALE) {
            System.out.printf("%1$s перешёл из %2$s в %3$s. \n", getName(), getMyArea().getName(), area.getName());
        } else if(this.getGender() == Gender.FEMALE){
            System.out.printf("%1$s перешла из %2$s в %3$s. \n", getName(), getMyArea().getName(), area.getName());
        }else {
            System.out.printf("%1$s перешло из %2$s в %3$s. \n", getName(), getMyArea().getName(), area.getName());
        }
        setArea(area);
    }*/

    public void setArea(Area area){
        myArea = area;
        /*if(!(area == null)) {
            area.addObject(this);
        }*/
    }

    public Area getMyArea(){
        return myArea;
    }

    public String getName(){
        return name;
    }

    /*public void moveToArea(AreaPart part) {
        getMyArea().removeObject(this);
        Area area = null;
        for(Object ob: getMyArea().getObjects(part)){
            if((ob.getClass() == Area.class)||(ob.getClass().getSuperclass() == Area.class)){
                area = (Area) ob;
            }
        }
        if(area.isPassable()) {
            if (this.getGender() == Gender.MALE) {
                System.out.printf("%1$s перешёл из %2$s в %3$s. \n", getName(), getMyArea().getName(), area.getName());
            } else if (this.getGender() == Gender.FEMALE) {
                System.out.printf("%1$s перешла из %2$s в %3$s. \n", getName(), getMyArea().getName(), area.getName());
            } else {
                System.out.printf("%1$s перешло из %2$s в %3$s. \n", getName(), getMyArea().getName(), area.getName());
            }
            setArea(area);
        }else{
            System.out.printf("%1$s не может идти на %2$s. Там %3$s. \n", getName(), AreaPart.getName(part), area.getName());
        }
    }*/

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getName().equals(((Mumi) obj).getName());
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void action(String s) {
        if(s == null){
            throw new NullLine("Empty action");
        }
        System.out.printf("%1$s %2$s ", this.getName(), s);
    }

    @Override
    public void show(String s, TextPosition pos) {
        if(pos == TextPosition.BEFORE) {
            System.out.printf("%1$s %2$s ", s, this.getName());
        }
    }

    @Override
    public int compareTo(Mumi o) {
        if(this.getName().length() != o.getName().length()){
            return this.getName().length() - o.getName().length();
        } else {
            if(this.getName().equals(o.getName())){
                return 0;
            }
          return 1;
        }
    }
}
