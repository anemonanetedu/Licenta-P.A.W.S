package com.example.myapplication;

public class Pet {

    private String name;
    private String age;
    private String weight;
    private String rfidTag;
    private String race;

    private String type;
    public Pet(){}

    public Pet(String name, String age, String weight, String rfidTag, String race, String type) {

        this.name = name;
        this.age = age;
        this.weight = weight;
        this.rfidTag = rfidTag;
        this.race = race;
        this.type = type;
    }

    public void setName(String name){ this.name = name; }

    public void setAge(String age){ this.age = age; }

    public void setWeight(String weight){ this.weight = weight; }

    public void setRfidTag(String rfidTag){ this.rfidTag = rfidTag; }

    public void setRace(String race){ this.race = race; }

    public String getName(){ return name; }
    public String getAge(){ return age; }

    public String getWeight(){ return weight; }

    public String getRfidTag(){ return rfidTag; }

    public String getRace(){ return race; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
