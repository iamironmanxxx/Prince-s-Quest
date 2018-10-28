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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Users(String uid, String name, int level) {
        this.uid = uid;
        this.name = name;
        this.level = level;
    }

    private String uid;
    private String name;
    private int level;

    public void setLevelSP(SharedPreference SP,int level){
        SP.set("user_level",level);
    }

    public int getLevelSP(SharedPreference SP){
        return SP.getInt("user_level");
    }

}
