package it.polito.oop.futsal;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    TreeMap<String, List<Field>> totReservations = new TreeMap<>();
	    
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
        if(!fields.containsKey(field) || !associates.containsKey(associate)) throw new FutsalException();
        String timeString[]= time.split(":");
        Integer timeMin = Integer.parseInt(timeString[0])*60 + Integer.parseInt(timeString[1]);
        String openString[] = openingTime.split(":");
        Integer openMin = Integer.parseInt(openString[0])*60 + Integer.parseInt(openString[1]);
        Integer difference = timeMin-openMin;
        if(difference%60 != 0) throw new FutsalException();
        fields.get(field).addReservations(time, associates.get(associate));
        
        if(!totReservations.containsKey(time)){
            LinkedList<Field> l = new LinkedList<>();
            l.add(fields.get(field));
            totReservations.put(time, l);
        }
        else{
            totReservations.get(time).add(fields.get(field));
        }
    }

    public boolean isBooked(int field, String time) {
        if(fields.get(field).getReservations().containsKey(time)) return true;
        return false;
    }
    

    public int getOccupation(int field) {
        return fields.get(field).getReservations().size();
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        return fields.values().stream().filter(f-> !isBooked(f.getField(), time))
        .filter(f-> f.isAcceptable(required.indoor, required.heating,required.ac))
        .sorted(Comparator.comparing(Field::getOccupation).reversed().thenComparing(Field::getField))
        .collect(Collectors.toList());
    }
    
    public long countServedAssociates() {
        return fields.values().stream().flatMap(f->f.getReservations().values().stream()).distinct().count();
    }
    
    public Map<Integer,Long> fieldTurnover() {
        Map<Integer, Long> map =  fields.values().stream().collect(Collectors.toMap(Field::getNumber, Field::getSizeLong, (d1,d2)->d1, TreeMap::new));
        return map;
    }
    
    public double occupation() {
        int totRes = (int)totReservations.values().stream().flatMap(List::stream).count();

        String openingString[]= openingTime.split(":");
        Integer openingMin = Integer.parseInt(openingString[0])*60 + Integer.parseInt(openingString[1]);
        String closingString[]= closingTime.split(":");
        Integer closingMin = Integer.parseInt(closingString[0])*60 + Integer.parseInt(closingString[1]);
        int totSlot = (closingMin-openingMin) / 60;
        return (totRes/totSlot);
    }
    
 }
