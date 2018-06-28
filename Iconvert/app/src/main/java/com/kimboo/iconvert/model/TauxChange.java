package com.kimboo.iconvert.model;

/**
 * Created by khranyt on 29/10/2015.
 */
public class TauxChange {

    private Long tauxChangeId;
    private String code;
    private Double tauxChange;

    private String mName;
    private String mFullname;

    public TauxChange() {
        this(null, null, null);
    }

    public TauxChange(String name, String fullname){
        this.mName = name;
        this.mFullname = fullname;
    }
    public TauxChange(String code, Double tauxChange){
        this(null, code, tauxChange);
    }

    public TauxChange(Long tauxChangeId, String code, Double tauxChange){
        this.tauxChangeId = tauxChangeId;
        this.code = code;
        this.tauxChange = tauxChange;
    }


    public String getName() {
        return this.mName;
    }

    public String getFullname() {
        return this.mFullname;
    }

    public Long getTauxChangeId() {
        return tauxChangeId;
    }

    public void setTauxChangeId(Long tauxChangeId) {
        this.tauxChangeId = tauxChangeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTauxChange() {
        return tauxChange;
    }

    public void setTauxChange(Double tauxChange) {
        this.tauxChange = tauxChange;
    }

    @Override
    public String toString() {
        return "TauxChange{" +
                "tauxChangeId=" + tauxChangeId +
                ", code='" + code + '\'' +
                ", tauxChange=" + tauxChange +
                '}';
    }
}
