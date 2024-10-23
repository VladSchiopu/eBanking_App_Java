package org.poo.cb;

import java.util.HashMap;
import java.util.Map;

public class RateDeSchimb {
    private static RateDeSchimb instanta;
    private Map<String, Map<String, Double>> rate;

    private RateDeSchimb() {
        rate = new HashMap<>();
    }

    public static RateDeSchimb Instanta() {
        if (instanta == null) {
            instanta = new RateDeSchimb();
        }
        return instanta;
    }

    public void addCurrency(String curr) {
            this.rate.put(curr, new HashMap<>());
    }


    public void addExchangeRate(String sursa, String destinatie, double rate) {
        if (!this.rate.containsKey(sursa)) {
            this.rate.put(sursa, new HashMap<>());
        }
        this.rate.get(sursa).put(destinatie, rate);
    }


    public Double getExchangeRate(String sursa, String destinatie) {
        if (rate.containsKey(sursa)) {
            Map<String, Double> destinationRates = rate.get(sursa);
            if (destinationRates.containsKey(destinatie)) {
                return destinationRates.get(destinatie);
            }
        }
        return null;
    }

}
