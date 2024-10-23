package org.poo.cb;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

public class Aplicatie {
    private static Aplicatie instanta;
    private Map<String, Utilizator> utilizatori;
    private Map<String, Actiuni> actiuni;

    private Aplicatie() {
        utilizatori = new HashMap<>();
        actiuni = new HashMap<>();
    }

    public static Aplicatie Instanta() {
        if (instanta == null) {
            instanta = new Aplicatie();
        }
        return instanta;
    }

    public Map<String, Utilizator> getUtilizatori() {
        return utilizatori;
    }

    public Map<String, Actiuni> getActiuni() {
        return actiuni;
    }

    public Utilizator getUtilizatorByEmail(String email) {
        return utilizatori.get(email);
    }


    public void adaugaUtilizator(Utilizator utilizator) {
        utilizatori.put(utilizator.getEmail(), utilizator);
    }

    public void citesteRateDeSchimb(String file) {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] valute = reader.readNext();

            for (int i = 1; i < valute.length; i++) {
                RateDeSchimb.Instanta().addCurrency(valute[i]);
            }

            String[] linie;
            while ((linie = reader.readNext()) != null) {
                String currency = linie[0];
                for (int i = 1; i < linie.length; i++) {
                    RateDeSchimb.Instanta().addExchangeRate(currency, valute[i], Double.parseDouble(linie[i]));
                }
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void citesteValoriStockuri(String file) {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] linie;
            reader.readNext();

            while ((linie = reader.readNext()) != null) {
                String numeActiune = linie[0];
                Actiuni actiune = new Actiuni(numeActiune);

                for (int i = 1; i < linie.length; i++) {
                    actiune.addValue(i - 1, Double.parseDouble(linie[i]));
                }

                this.actiuni.put(numeActiune, actiune);
            }

        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}