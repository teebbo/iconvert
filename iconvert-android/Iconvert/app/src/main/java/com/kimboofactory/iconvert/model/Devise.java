package com.kimboofactory.iconvert.model;

/**
 * Created by khranyt on 31/03/2017.
 */

public class Devise {

    private Long deviseId;
    private String code;
    private String libelle;

    public Devise(){
        this(null, null, null);
    }

    public Devise(String code, String libelle) {
        this(null, code, libelle);
    }

    public Devise(Long deviseId, String code, String libelle) {
        this.deviseId = deviseId;
        this.code = code;
        this.libelle = libelle;
    }

    public Long getDeviseId() {
        return deviseId;
    }

    public void setDeviseId(Long deviseId) {
        this.deviseId = deviseId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Devise{" +
                "deviseId=" + deviseId +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
