package com.superclassroom;

/**
 * Created by Timber on 2017/11/3.
 */

public class classroom {
    private String name;
    private int stime;
    private int etime;
    private String stage;
    private String username;
    public String getName()
    {
        return name;
    }
    public int getStime() { return stime;}
    public int getEtime() { return etime; }
    public String getStage()
    {
        return stage;
    }
    public String getUsername(){ return username;  }
    public classroom(String name, int stime,int etime, String stage,String username) {
        super();
        this.name = name;
        this.stime = stime;
        this.etime = etime;
        this.stage = stage;
        this.username = username;
    }


}
