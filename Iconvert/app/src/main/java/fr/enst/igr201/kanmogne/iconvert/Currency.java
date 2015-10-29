package fr.enst.igr201.kanmogne.iconvert;

/**
 * Created by Joffrey KanMoney on 29/10/2015.
 */
public class Currency {

    private String mName;
    private String mFullname;

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
}
