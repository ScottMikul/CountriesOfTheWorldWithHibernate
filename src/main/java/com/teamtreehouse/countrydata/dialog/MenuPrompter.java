package com.teamtreehouse.countrydata.dialog;

import com.teamtreehouse.countrydata.model.Country;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by scott on 5/3/2017.
 */
public class MenuPrompter {
    private List<String> options = new ArrayList<>();
    private boolean exit = false;
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Country Data With Hibernate. Your options are: ");
        System.out.println("*****************************");
        int count = 1;
        for(String o : options){
            System.out.printf("%d)%s%n",count++,o);
        }
        System.out.println("*****************************");
    }
    public MenuPrompter(){
        options.add("View Countries");
        options.add("View Maximums");
        options.add("View Minimums");
        options.add("Edit Country");
        options.add("Add Country");
        options.add("Delete Country");
        options.add("Exit");
    }

    public boolean exiting(){
        return exit;
    }

    public int getPrompt() {
        return scanner.nextInt();
    }

    public void printAllColumns(List<Country> country){
        System.out.println("Countries: ");
        System.out.println("---------------------");
        System.out.printf("%27s name %8s Adult Literacy Rate %8s Internet Users %8s %25s %n","","","","","literacy rate / internet usage");
        for(int i=0;i<100;i++){
            System.out.print("-");
        }
        System.out.println();
        int count =1;
        for (Country c : country) {
            String content = "%d) %32s %28s %21s %30s";
            content = String.format(content, count++, c.getName(),
                    c.getAdultLiteracyRate()!=null?c.getAdultLiteracyRate().setScale(2, RoundingMode.CEILING):null,
                    c.getInternetUsers()!=null?c.getInternetUsers().setScale(2,RoundingMode.CEILING):null,
                    (c.getInternetUsers()!=null&&c.getAdultLiteracyRate()!=null)?c.getAdultLiteracyRate().divide(c.getInternetUsers(),2,RoundingMode.CEILING):null);
            System.out.println(content);


        }
    }


    public void setExit(boolean exit) {
        this.exit= exit;
    }

    public Country promptForCountry(String action, List<Country> countries) {
        System.out.println("which country would you like to "+ action+"?");
        printAllColumns(countries);
        return countries.get(scanner.nextInt()-1);
    }


    public Country  promptForUpdateData(Country country) {
        System.out.printf("What is the new name? ( old name : %s)%n", country.getName() );
        String newName = scanner.next();
        System.out.printf("What is the new literacy rate? ( old literacy rate: : %f ) %n", country.getAdultLiteracyRate());
        BigDecimal newLit = scanner.nextBigDecimal();
        System.out.printf("What is the new internet users? ( old interent users: : %f ) %n", country.getInternetUsers());
        BigDecimal newUsers = scanner.nextBigDecimal();
        return new Country.CountryBuilder().withAdultLiteracyRate(newLit)
                .withName(newName)
                .withInternetUsers(newUsers).build();
    }

    public Country  promptForNewCountryData(List<Country> countries) {

        String code = "";
        do {
            if(countries.contains(code)){
                System.out.println("Code: "+ code+" is taken. Please enter a unique code.");
            }
            System.out.printf("What is the country code? %n");
            code = scanner.next();
        }while (countries.contains(code));
        System.out.printf("What is the name of the country? %n");
        String name = scanner.next();
        System.out.printf("What is the literacy rate? %n");
        BigDecimal lit = scanner.nextBigDecimal();
        System.out.printf("What is the internet users rate?  %n");
        BigDecimal users = scanner.nextBigDecimal();
        return new Country.CountryBuilder().withAdultLiteracyRate(lit)
                .withName(name)
                .withCode(code)
                .withInternetUsers(users).build();
    }
}
