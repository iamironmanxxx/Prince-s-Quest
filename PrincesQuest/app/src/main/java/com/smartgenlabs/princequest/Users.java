package com.smartgenlabs.princequest;

public class Users {
    public Users()
    {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Users(String uid, String name, String level) {
        this.uid = uid;
        this.name = name;
        this.level = level;
    }

    private String uid;
    private String name;
    private String level;

}
