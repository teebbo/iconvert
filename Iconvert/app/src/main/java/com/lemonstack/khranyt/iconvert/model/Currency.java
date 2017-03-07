package com.lemonstack.khranyt.iconvert.model;

/**
 * Created by khranyt on 29/10/2015.
 */
public class Currency {

    private String mName;
    private String mFullname;

    public Currency() {}
    public Currency(String name, String fullname){
        this.mName = name;
        this.mFullname = fullname;
    }

    public String getName() {
        return this.mName;
    }

    public String getFullname() {
        return this.mFullname;
    }

    @Override
    public String toString() {
        return "[ " + this.mName + " - " + this.mFullname + " ]";
    }


}
