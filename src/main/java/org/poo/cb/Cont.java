package org.poo.cb;

public class Cont {
    private String tipValuta;
    private double sumaTotala;

    public Cont(String tipValuta) {
        this.tipValuta = tipValuta;
        this.sumaTotala = 0;
    }

    public String getTipValuta() {
        return tipValuta;
    }

    public Double getSumaTotala() {
        return sumaTotala;
    }

    public void adaugaSuma(double sumaAdaugata) {
        sumaTotala += sumaAdaugata;
    }

    public void retrageSuma(double sumaRetrasa) {
        if (sumaRetrasa <= sumaTotala) {
            sumaTotala -= sumaRetrasa;
        }
    }
}

