package org.poo.cb;

import java.util.Map;

public class UtilizatorPremium extends Utilizator {
    protected Utilizator utilizatorDecorat;

    public UtilizatorPremium (Utilizator utilizator){
        this.utilizatorDecorat = utilizator;
    }

    @Override
    public void exchangeMoney(String valutaSursa, String valutaDest, String suma) {
        Cont contSursa = utilizatorDecorat.getConturi().get(valutaSursa);
        Cont contDest = utilizatorDecorat.getConturi().get(valutaDest);

        Double sumaSursa = Double.parseDouble(suma) * RateDeSchimb.Instanta().getExchangeRate(valutaDest, valutaSursa);

        contSursa.retrageSuma(sumaSursa);
        contDest.adaugaSuma(Double.parseDouble(suma));

    }

    @Override
    public void buyStocks(String numeCompanie, String cantitate){

        Cont cont = utilizatorDecorat.getConturi().get("USD");
        Actiuni actiune = Aplicatie.Instanta().getActiuni().get(numeCompanie);
        Actiuni actiuneProprie = new Actiuni(actiune.getNumeCompanie());
        actiuneProprie.setValues(actiune.getValoriUltimele10Zile());
        actiuneProprie.addCantitate(Integer.parseInt(cantitate));
        Double suma = Double.parseDouble(cantitate) * actiune.getValoriUltimele10Zile()[9];

        SMACrossoverStrategy strategy = new SMACrossoverStrategy();
        Map<String, Actiuni> recomandate = strategy.recommend(Aplicatie.Instanta().getActiuni());



        if(cont.getSumaTotala() < suma)
        {
            System.out.println("Insufficient amount in account for buying stock");
        }
        else {
            if(recomandate.containsKey(numeCompanie)){
                cont.retrageSuma(suma-suma*5/100);
                utilizatorDecorat.adaugaActiuni(actiuneProprie);

            } else {
                cont.retrageSuma(suma);
                utilizatorDecorat.adaugaActiuni(actiuneProprie);
            }
        }
    }
}

