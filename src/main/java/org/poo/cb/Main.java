package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        if(args == null) {
            System.out.println("Running Main");
        }

        else{
            Aplicatie app = Aplicatie.Instanta();
            String path = System.getProperty("user.dir") + "/src/main/resources/";

            app.citesteRateDeSchimb(path + args[0]);
            app.citesteValoriStockuri(path + args[1]);


            try{
                BufferedReader br = new BufferedReader(new FileReader(path + args[2]));

                String linie = br.readLine();

                while (linie!=null)
                {
                    String[] argumente = linie.split(" ");

                    if(argumente[0].equals("CREATE")&&argumente[1].equals("USER"))
                    {
                        if(app.getUtilizatori().containsKey(argumente[2])){
                            System.out.println("User with " +argumente[2]+ " already exists");
                        }
                        else {

                            String[] argumenteComanda = new String[4];
                            argumenteComanda[0] = argumente[2];
                            argumenteComanda[1] = argumente[3];
                            argumenteComanda[2] = argumente[4];
                            argumenteComanda[3] = argumente[5];

                            for (int i = 6; i < argumente.length; i++) {
                                argumenteComanda[3] += " ";
                                argumenteComanda[3] += argumente[i];
                            }

                            ComandaCreateUser ccu = new ComandaCreateUser();
                            ccu.executa(argumenteComanda);
                        }
                    } else if(argumente[0].equals("LIST")&&argumente[1].equals("USER"))
                    {
                        if(app.getUtilizatori().containsKey(argumente[2])){

                            String[] argumenteComanda = new String[1];
                            argumenteComanda[0]=argumente[2];

                            ComandaListUser clu = new ComandaListUser();
                            clu.executa(argumenteComanda);

                        }
                        else {
                            System.out.println("User with " +argumente[2]+ " doesn't exist");
                        }

                    }
                    else if(argumente[0].equals("ADD")&&argumente[1].equals("FRIEND"))
                    {
                        if(app.getUtilizatori().containsKey(argumente[2])) {
                            if (app.getUtilizatori().containsKey(argumente[3])) {
                                Utilizator utilizator = app.getUtilizatori().get(argumente[2]);
                                if (utilizator.getPrieteni().containsKey(argumente[3])) {
                                    System.out.println("User with " + argumente[3] + " is already a friend");
                                } else {

                                    String[] argumenteComanda = new String[2];
                                    argumenteComanda[0]=argumente[2];
                                    argumenteComanda[1]=argumente[3];

                                    ComandaAddFriend caf = new ComandaAddFriend();
                                    caf.executa(argumenteComanda);
                                }

                            }
                            else {
                                System.out.println("User with " +argumente[3]+ " doesn't exist");
                            }
                        }
                        else {
                            System.out.println("User with " +argumente[2]+ " doesn't exist");
                        }

                    }

                    else if(argumente[0].equals("ADD")&&argumente[1].equals("ACCOUNT"))
                    {
                        Utilizator utilizator = app.getUtilizatori().get(argumente[2]);
                        if(utilizator.getConturi().containsKey(argumente[3])) {
                            System.out.println("Account in currency " + argumente[3] +" already exists for user");
                        }
                        else {
                            String[] argumenteComanda = new String[2];
                            argumenteComanda[0]=argumente[2];
                            argumenteComanda[1]=argumente[3];

                            ComandaAddAccount caa = new ComandaAddAccount();
                            caa.executa(argumenteComanda);

                        }

                    }

                    else if(argumente[0].equals("ADD")&&argumente[1].equals("MONEY"))
                    {

                        String[] argumenteComanda = new String[3];
                        argumenteComanda[0]=argumente[2];
                        argumenteComanda[1]=argumente[3];
                        argumenteComanda[2]=argumente[4];

                        ComandaAddMoney cam = new ComandaAddMoney();
                        cam.executa(argumenteComanda);

                    }

                    else if(argumente[0].equals("LIST")&&argumente[1].equals("PORTFOLIO"))
                    {

                        if(app.getUtilizatori().containsKey(argumente[2])){

                            String[] argumenteComanda = new String[1];
                            argumenteComanda[0]=argumente[2];

                            ComandaListPortfolio clp = new ComandaListPortfolio();
                            clp.executa(argumenteComanda);

                        }
                        else {
                            System.out.println("User with " +argumente[2]+ " doesen't exist");
                        }


                    }

                    else if(argumente[0].equals("EXCHANGE")&&argumente[1].equals("MONEY"))
                    {
                        Utilizator utilizator = app.getUtilizatorByEmail(argumente[2]);
                        Double rata = RateDeSchimb.Instanta().getExchangeRate(argumente[4],argumente[3]);
                        if(utilizator.getConturi().get(argumente[3]).getSumaTotala()>=Double.parseDouble(argumente[5])*rata)
                        {
                            String[] argumenteComanda = new String[4];
                            argumenteComanda[0]=argumente[2];
                            argumenteComanda[1]=argumente[3];
                            argumenteComanda[2]=argumente[4];
                            argumenteComanda[3]=argumente[5];

                            ComandaExchangeMoney cem = new ComandaExchangeMoney();
                            cem.executa(argumenteComanda);

                        }
                        else {
                            System.out.println("Insufficient amount in account " +argumente[3]+ " for exchange");
                        }

                    }

                    else if(argumente[0].equals("TRANSFER")&&argumente[1].equals("MONEY"))
                    {
                        Utilizator utilizator = app.getUtilizatorByEmail(argumente[2]);

                        if(utilizator.getPrieteni().containsKey(argumente[3]))
                        {
                            if(utilizator.getConturi().get(argumente[4]).getSumaTotala()>=Double.parseDouble(argumente[5])) {
                                String[] argumenteComanda = new String[4];
                                argumenteComanda[0] = argumente[2];
                                argumenteComanda[1] = argumente[3];
                                argumenteComanda[2] = argumente[4];
                                argumenteComanda[3] = argumente[5];

                                ComandaTransferMoney ctm = new ComandaTransferMoney();
                                ctm.executa(argumenteComanda);
                            }
                            else {
                                System.out.println("Insufficient amount in account " +argumente[4]+ " for transfer");
                            }

                        }
                        else {
                            System.out.println("You are not allowed to transfer money to " + argumente[3]);
                        }

                    }

                    else if(argumente[0].equals("RECOMMEND")&&argumente[1].equals("STOCKS"))
                    {
                        String[] argumenteComanda = new String[1];


                        ComandaRecommendStocks crs = new ComandaRecommendStocks();
                        crs.executa(argumenteComanda);

                    }

                    else if(argumente[0].equals("BUY")&&argumente[1].equals("STOCKS"))
                    {
                        String[] argumenteComanda = new String[3];

                        argumenteComanda[0] = argumente[2];
                        argumenteComanda[1] = argumente[3];
                        argumenteComanda[2] = argumente[4];


                        ComandaBuyStocks cbs = new ComandaBuyStocks();
                        cbs.executa(argumenteComanda);

                    }
                    else if(argumente[0].equals("BUY")&&argumente[1].equals("PREMIUM"))
                    {
                        String[] argumenteComanda = new String[1];

                        argumenteComanda[0] = argumente[2];

                        ComandaBuyPremium cbp = new ComandaBuyPremium();
                        cbp.executa(argumenteComanda);
                    }
                    linie = br.readLine();
                }

            } catch (IOException e) {
            }

            System.out.println();
            app.getUtilizatori().clear();
            app.getActiuni().clear();
        }


    }
}