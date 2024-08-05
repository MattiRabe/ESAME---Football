package it.polito.oop.futsal;

public class Associate {

    private final static int INCREMENT = 1;
    private static int BASE = 0;

    private String first;
    private String last;
    private String phone;
    private Integer id;


    public Associate(String first, String last, String phone) {
        this.first = first;
        this.last = last;
        this.phone = phone;
        this.id= INCREMENT + BASE++;
    }


    public String getFirst() {
        return first;
    }


    public String getLast() {
        return last;
    }


    public String getPhone() {
        return phone;
    }


    public Integer getId() {
        return id;
    }

    
    

}
