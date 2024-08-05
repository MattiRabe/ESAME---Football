package it.polito.oop.futsal;

public class Field {

    private final static int INCREMENT = 1;
    private static int BASE = 0;


    private boolean indoor;
    private boolean heating;
    private boolean ac;
    private int number;

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
}
