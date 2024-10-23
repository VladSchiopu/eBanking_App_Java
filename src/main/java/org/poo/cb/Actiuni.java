package org.poo.cb;


public class Actiuni {
    private String numeCompanie;
    private Double[] valoriUltimele10Zile;
    private int cantitate;
    public Actiuni(String numeCompanie) {
        this.numeCompanie = numeCompanie;
        this.valoriUltimele10Zile = new Double[10];
        this.cantitate = 0;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    public void addValue(int index, Double value) {
        this.valoriUltimele10Zile[index] = value;
    }

    public void setValues(Double[] values) {
        this.valoriUltimele10Zile = values;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void addCantitate(int cantitateAdaugata) {
        this.cantitate += cantitateAdaugata;
    }

    public Double[] getValoriUltimele10Zile() {
        return valoriUltimele10Zile;
    }

}
