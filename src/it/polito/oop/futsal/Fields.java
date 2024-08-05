package it.polito.oop.futsal;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {

    private String openingTime;
    private String closingTime;
    TreeMap<Integer, Field> fields = new TreeMap<>();
    TreeMap<Integer, Associate> associates = new TreeMap<>();
	    
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
    }
    
	
    public void defineFields(Features... features) throws FutsalException {
        for(Features f : features){
            if(f.indoor==false){
                if(f.heating==true || f.ac==true) throw new FutsalException(); 
            }
            Field fd = new Field(f.indoor, f.heating, f.ac);
            fields.put(fd.getNumber(), fd);
        }
    }
    
    public long countFields() {
        return fields.size();
    }

    public long countIndoor() {
        return fields.values().stream().filter(Field::isIndoor).count();
    }
    
    public String getOpeningTime() {
        return this.openingTime;
    }
    
    public void setOpeningTime(String time) {
        this.openingTime=time;
    }
    
    public String getClosingTime() {
        return this.closingTime;
    }
    
    public void setClosingTime(String time) {
        this.closingTime=time;
    }

    public int newAssociate(String first, String last, String mobile) {
        Associate a = new Associate(first, last, mobile);
        associates.put(a.getId(), a);
        return a.getId();
    }
    
    public String getFirst(int associate) throws FutsalException {
        if(!associates.containsKey(associate)) throw new FutsalException();
        return associates.get(associate).getFirst();
    }
    
    public String getLast(int associate) throws FutsalException {
        if(!associates.containsKey(associate)) throw new FutsalException();
        return associates.get(associate).getLast();
    }
    
    public String getPhone(int associate) throws FutsalException {
        if(!associates.containsKey(associate)) throw new FutsalException();
        return associates.get(associate).getPhone();
    }
    
    public int countAssociates() {
        return associates.size();
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    }

    public boolean isBooked(int field, String time) {
        return false;
    }
    

    public int getOccupation(int field) {
        return -1;
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        return null;
    }
    
    public long countServedAssociates() {
        return -1;
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return null;
    }
    
    public double occupation() {
        return -1;
    }
    
 }
