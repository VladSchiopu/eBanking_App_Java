package org.poo.cb;

import java.util.Map;

public abstract class Comanda {

    public Comanda(){}
    public abstract void executa(String[] argumente);
}

class ComandaCreateUser extends Comanda {

    public ComandaCreateUser() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String email = argumente[0];
        String nume = argumente[1];
        String prenume = argumente[2];
        String adresa = argumente[3];

        Utilizator utilizator = new Utilizator(email, nume, prenume, adresa);
        Aplicatie.Instanta().adaugaUtilizator(utilizator);
    }
}

class ComandaListUser extends Comanda {

    public ComandaListUser() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String email = argumente[0];

        Utilizator utilizator = Aplicatie.Instanta().getUtilizatori().get(email);
        System.out.println(utilizator.toJsonString());
    }
}

class ComandaListPortfolio extends Comanda {

    public ComandaListPortfolio() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String email = argumente[0];

        Utilizator utilizator = Aplicatie.Instanta().getUtilizatori().get(email);
        System.out.println(utilizator.portfolio());
    }
}

class ComandaAddFriend extends Comanda {

    public ComandaAddFriend() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String userEmail = argumente[0];
        String friendEmail = argumente[1];

        Utilizator utilizator = Aplicatie.Instanta().getUtilizatori().get(userEmail);
        Utilizator prieten = Aplicatie.Instanta().getUtilizatori().get(friendEmail);
        utilizator.adaugaPrieten(friendEmail);
        prieten.adaugaPrieten(userEmail);
    }
}

class ComandaAddAccount extends Comanda {

    public ComandaAddAccount() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String userEmail = argumente[0];
        String valuta = argumente[1];

        Cont cont = new Cont(valuta);
        Aplicatie.Instanta().getUtilizatorByEmail(userEmail).adaugaCont(cont);
    }
}

class ComandaAddMoney extends Comanda {

    public ComandaAddMoney() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String userEmail = argumente[0];
        String valuta = argumente[1];
        String suma = argumente[2];

        Cont cont = Aplicatie.Instanta().getUtilizatorByEmail(userEmail).getConturi().get(valuta);
        cont.adaugaSuma(Double.parseDouble(suma));
    }
}

class ComandaExchangeMoney extends Comanda {

    public ComandaExchangeMoney() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String userEmail = argumente[0];
        String valutaSursa = argumente[1];
        String valutaDest = argumente[2];
        String suma = argumente[3];

        Utilizator utilizator = Aplicatie.Instanta().getUtilizatorByEmail(userEmail);
        if(utilizator.isPremium()){
            UtilizatorPremium utilizatorPremium = new UtilizatorPremium(utilizator);
            utilizatorPremium.exchangeMoney(valutaSursa,valutaDest,suma);
        }
        else {
            utilizator.exchangeMoney(valutaSursa,valutaDest,suma);
        }

    }
}

class ComandaTransferMoney extends Comanda {

    public ComandaTransferMoney() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String userEmail = argumente[0];
        String friendEmail = argumente[1];
        String valuta = argumente[2];
        String suma = argumente[3];

        Cont contSursa = Aplicatie.Instanta().getUtilizatorByEmail(userEmail).getConturi().get(valuta);
        Cont contDest = Aplicatie.Instanta().getUtilizatorByEmail(friendEmail).getConturi().get(valuta);

        contSursa.retrageSuma(Double.parseDouble(suma));
        contDest.adaugaSuma(Double.parseDouble(suma));

    }
}

class ComandaRecommendStocks extends Comanda {

    private SMACrossoverStrategy strategy;
    public ComandaRecommendStocks() {
        super();
        this.strategy = new SMACrossoverStrategy();
    }

    @Override
    public void executa(String[] argumente) {

        Map<String, Actiuni> recomandate = strategy.recommend(Aplicatie.Instanta().getActiuni());

        strategy.afiseaza(recomandate);

    }
}

class ComandaBuyStocks extends Comanda {

    public ComandaBuyStocks() {
        super();
    }

    @Override
    public void executa(String[] argumente) {
        String userEmail = argumente[0];
        String numeCompanie = argumente[1];
        String cantitate = argumente[2];

        Utilizator utilizator = Aplicatie.Instanta().getUtilizatorByEmail(userEmail);
        if(utilizator.isPremium()){
            UtilizatorPremium utilizatorPremium = new UtilizatorPremium(utilizator);
            utilizatorPremium.buyStocks(numeCompanie, cantitate);
        }
        else {
            utilizator.buyStocks(numeCompanie, cantitate);
        }

    }
}

class ComandaBuyPremium extends Comanda {

    public ComandaBuyPremium() {
        super();
    }

    @Override
    public void executa(String[] argumente) {

        String userEmail = argumente[0];

        Utilizator utilizator = Aplicatie.Instanta().getUtilizatorByEmail(userEmail);
        Cont cont = utilizator.getConturi().get("USD");

        if(cont.getSumaTotala()>=100){
            cont.retrageSuma(100);
            utilizator.goPremium();

        }


    }
}
