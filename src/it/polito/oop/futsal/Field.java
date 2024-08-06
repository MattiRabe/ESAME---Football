package it.polito.oop.futsal;

import java.util.TreeMap;
import java.util.TreeSet;

public class Field implements FieldOption {

    private final static int INCREMENT = 1;
    private static int BASE = 0;


    private boolean indoor;
    private boolean heating;
    private boolean ac;
    private int number;
    private TreeMap<String, Associate> reservations = new TreeMap<>();

    public Field(boolean indoor, boolean heating, boolean ac) {
        this.indoor = indoor;
        this.heating = heating;
        this.ac = ac;
        this.number= INCREMENT + BASE++;
    } 

    public boolean isIndoor() {
        return indoor;
    }
    public boolean isHeating() {
        return heating;
    }
    public boolean isAc() {
        return ac;
    }
    public Integer getNumber() {
        return number;
    }

    public TreeMap<String, Associate> getReservations() {
        return reservations;
    }

    public void addReservations(String time, Associate a) {
        reservations.put(time, a);
    }

    @Override
    public int getField() {
        return number;
    }

    @Override
    public int getOccupation() {
        return reservations.size();
    }

    public boolean isAcceptable(boolean indoor, boolean heating, boolean ac){
        if(indoor==true){
            if(this.indoor==false) return false;
        } 
        if(heating==true){
            if(this.heating==false) return false;
        }
        if(ac==true){
            if(this.ac==false) return false;
        }
        return true;
    }

    public Long getSizeLong() {
        return (long)reservations.size();
    }
    
    
}
