package org.poo.cb;

import java.util.*;

public class Utilizator {
    private String email;
    private String nume;
    private String prenume;
    private String adresa;
    private Map<String, Cont> conturi;
    private Map<String, Actiuni> actiuni;
    private Map<String, Utilizator> prieteni;
    private boolean premium;

    public Utilizator(){}

    public Utilizator(String email, String nume, String prenume, String adresa) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.conturi = new LinkedHashMap<>();
        this.actiuni = new LinkedHashMap<>();
        this.prieteni = new HashMap<>();
        this.premium = false;
    }

    public String getEmail() {
        return email;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public boolean isPremium() {
        return premium;
    }

    public Map<String, Cont> getConturi() {
        return conturi;
    }

    public Map<String, Actiuni> getActiuni() {
        return actiuni;
    }

    public Map<String, Utilizator> getPrieteni() {
        return prieteni;
    }

    public void adaugaCont(Cont cont) {
        conturi.put(cont.getTipValuta(),cont);
    }

    public void adaugaActiuni(Actiuni actiune) {
        actiuni.put(actiune.getNumeCompanie(),actiune);
    }

    public void adaugaPrieten(String email) {
        prieteni.put(email, Aplicatie.Instanta().getUtilizatori().get(email));
    }

    public void goPremium() {
        this.premium = true;
    }

    public void exchangeMoney(String valutaSursa, String valutaDest, String suma){


        Cont contSursa = conturi.get(valutaSursa);
        Cont contDest = conturi.get(valutaDest);

        Double sumaSursa = Double.parseDouble(suma) * RateDeSchimb.Instanta().getExchangeRate(valutaDest,valutaSursa);

        if(sumaSursa > contSursa.getSumaTotala()/2) sumaSursa += sumaSursa/100;
        if(sumaSursa>contSursa.getSumaTotala()) System.out.println("Insufficient ammount in account " + valutaSursa + " for exchange");
        else {
            contSursa.retrageSuma(sumaSursa);
            contDest.adaugaSuma(Double.parseDouble(suma));
        }
    }

    public void buyStocks(String numeCompanie, String cantitate){


        Cont cont = conturi.get("USD");
        Actiuni actiune = Aplicatie.Instanta().getActiuni().get(numeCompanie);
        Actiuni actiuneProprie = new Actiuni(actiune.getNumeCompanie());
        actiuneProprie.setValues(actiune.getValoriUltimele10Zile());
        actiuneProprie.addCantitate(Integer.parseInt(cantitate));
        Double suma = Double.parseDouble(cantitate) * actiune.getValoriUltimele10Zile()[9];


        if(cont.getSumaTotala() < suma)
        {
            System.out.println("Insufficient amount in account for buying stock");
        }
        else {
            cont.retrageSuma(suma);
            this.adaugaActiuni(actiuneProprie);
        }
    }

    public String toJsonString() {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{");
        jsonString.append("\"email\":\"").append(email).append("\",");
        jsonString.append("\"firstname\":\"").append(nume).append("\",");
        jsonString.append("\"lastname\":\"").append(prenume).append("\",");
        jsonString.append("\"address\":\"").append(adresa).append("\",");

        jsonString.append("\"friends\":[");
        for (Map.Entry<String, Utilizator> entry : prieteni.entrySet()) {
            jsonString.append("\"").append(entry.getKey()).append("\",");
        }
        if (!prieteni.isEmpty()) {
            jsonString.deleteCharAt(jsonString.length() - 1);
        }
        jsonString.append("]");
        jsonString.append("}");

        return jsonString.toString();
    }

    public String portfolio() {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{");


        jsonString.append("\"stocks\":[");
        for (Map.Entry<String, Actiuni> entry : actiuni.entrySet()) {
            jsonString.append("{");
            jsonString.append("\"stockName\":\"").append(entry.getKey()).append("\",");
            jsonString.append("\"amount\":").append(entry.getValue().getCantitate()).append("},");
        }
        if (!actiuni.isEmpty()) {
            jsonString.deleteCharAt(jsonString.length() - 1);
        }
        jsonString.append("],");

        jsonString.append("\"accounts\":[");
        for (Map.Entry<String, Cont> entry : conturi.entrySet()) {
            jsonString.append("{");
            jsonString.append("\"currencyName\":\"").append(entry.getKey()).append("\",");
            String formatted = String.format("%.2f", entry.getValue().getSumaTotala());
            jsonString.append("\"amount\":\"").append(formatted).append("\"},");
        }
        if (!conturi.isEmpty()) {
            jsonString.deleteCharAt(jsonString.length() - 1);
        }
        jsonString.append("]}");

        return jsonString.toString();
    }

}