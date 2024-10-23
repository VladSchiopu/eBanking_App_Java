package org.poo.cb;

import java.util.*;

public interface RecommendationStrategy {
    Map<String, Actiuni> recommend(Map<String, Actiuni> actiuni);
}

class SMACrossoverStrategy implements RecommendationStrategy {

    public SMACrossoverStrategy() {}

    public Map<String, Actiuni> recommend(Map<String, Actiuni> actiuni) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"stocksToBuy\": [");
        Map<String, Actiuni> recomandate = new LinkedHashMap<>();

        for (HashMap.Entry<String, Actiuni> entry : actiuni.entrySet()) {
            Actiuni actiune = entry.getValue();

            double shortTermSMA = calculatiSMA(actiune, 5);
            double longTermSMA = calculatiSMA(actiune, 10);

            if (shortTermSMA > longTermSMA) {
                recomandate.put(actiune.getNumeCompanie(),actiune);
            }
        }

        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        stringBuilder.append("]}");

        return recomandate;
    }

    private double calculatiSMA(Actiuni actiune, int period) {

        double sum = 0;
        int size = actiune.getValoriUltimele10Zile().length;
        for (int i = size-1; i >= size-period; i--) {
            sum += actiune.getValoriUltimele10Zile()[i];
        }
        return sum / period;
    }

    public void afiseaza(Map<String, Actiuni> recomandate)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"stocksToBuy\": [");

        for (HashMap.Entry<String, Actiuni> entry : recomandate.entrySet()) {
            Actiuni actiune = entry.getValue();

            double shortTermSMA = calculatiSMA(actiune, 5);
            double longTermSMA = calculatiSMA(actiune, 10);

            if (shortTermSMA > longTermSMA) {
                stringBuilder.append("\"").append(entry.getKey()).append("\",");
            }
        }

        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        stringBuilder.append("]}");

        System.out.println(stringBuilder.toString());
    }

}

