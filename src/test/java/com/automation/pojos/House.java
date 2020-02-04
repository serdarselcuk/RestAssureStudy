package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class House {
    @SerializedName("_id")
    String id;

    @SerializedName("__v")
    int v;

    private String name ,
    mascot,
    headOfHouse,
    founder,
    school;
    String[] values;
    String[] colors;

    private List<Map<String, String>> members;

    public House(String id, int v, String name, String mascot, String headOfHouse, String founder, String school, String[] values, String[] colors, List<Map<String, String>> members) {
        setId(id);
        this.v = v;
        this.name = name;
        this.mascot = mascot;
        this.headOfHouse = headOfHouse;
        this.founder = founder;
        this.school = school;
        this.values = values;
        this.colors = colors;
        this.members = members;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }


    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getHeadOfHouse() {
        return headOfHouse;
    }

    public void setHeadOfHouse(String headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<Map<String, String>> getMembers() {
        return members;
    }

    public void setMembers(List<Map<String, String>> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", v=" + v +
                ", name='" + name + '\'' +
                ", mascot='" + mascot + '\'' +
                ", headOfHouse='" + headOfHouse + '\'' +
                ", founder='" + founder + '\'' +
                ", school='" + school + '\'' +
                ", members=" + members +
                '}';
    }

    public House() {
    }
}
