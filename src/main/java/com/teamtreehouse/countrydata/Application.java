package com.teamtreehouse.countrydata;

import com.teamtreehouse.countrydata.dialog.MenuPrompter;
import com.teamtreehouse.countrydata.model.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

/**
 * Created by scott on 5/3/2017.
 */
public class Application {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        MenuPrompter prompter = new MenuPrompter();
        do {
            prompter.start();
            switch(prompter.getPrompt()){
                case 1: prompter.printAllColumns(findAllCountryData());
                        break;
                case 2: getMaximums();
                    break;
                case 3: getMinimums();
                    break;
                case 4: edit(prompter);
                    break;
                case 5: add(prompter);
                    break;
                case 6: delete(prompter);
                    break;
                case 7: prompter.setExit(true);
                    break;
            }
        }while(!prompter.exiting());
        System.out.println("Exiting.");

    }
    private static void delete(MenuPrompter prompter) {
        List<Country> countries= findAllCountryData();

        Country country = prompter.promptForCountry("Delete", countries);

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(country);

        session.getTransaction().commit();

        session.close();


    }

    private static void add(MenuPrompter prompter) {
        //System.out.println(findAllCountryData().get(213).getCode()); ZWE
        // from this I can tell that the code will have to be included in the creation
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(prompter.promptForNewCountryData(findAllCountryData()));

        session.getTransaction().commit();
        session.close();


    }

    private static void edit(MenuPrompter prompter) {
        List<Country> countries= findAllCountryData();

        Country country = prompter.promptForCountry("Edit", countries);

        Country newCountry = prompter.promptForUpdateData(country);

        country.setCountry(newCountry);

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.update(country);

        session.getTransaction().commit();

        session.close();

    }

    private static void getMinimums() {
        List<Country> countries= findAllCountryData();

        Country minInternetUsersCountry = countries.stream().
                reduce((c1,c2) ->  c1.getInternetUsers()==null ? c2 : (c2.getInternetUsers()==null ? c1 : (c1.getInternetUsers().compareTo(c2.getInternetUsers()) < 0 ? c1: c2))).get();

        Country minLiteracy = countries.stream().
                reduce((c1,c2) ->  c1.getAdultLiteracyRate()==null ? c2 : (c2.getAdultLiteracyRate()==null ? c1 : (c1.getAdultLiteracyRate().compareTo(c2.getAdultLiteracyRate()) < 0 ? c1: c2))).get();

        printMaxMin("Minimums", minInternetUsersCountry, minLiteracy);

    }

    private static void getMaximums() {
        List<Country> countries= findAllCountryData();

        Country maxInternetUsersCountry = countries.stream().
                reduce((c1,c2) ->  c1.getInternetUsers()==null ? c2 : (c2.getInternetUsers()==null ? c1 : (c1.getInternetUsers().compareTo(c2.getInternetUsers()) > 0 ? c1: c2))).get();

        Country maxLiteracy = countries.stream().
                reduce((c1,c2) ->  c1.getAdultLiteracyRate()==null ? c2 : (c2.getAdultLiteracyRate()==null ? c1 : (c1.getAdultLiteracyRate().compareTo(c2.getAdultLiteracyRate()) > 0 ? c1: c2))).get();

        printMaxMin("Maximums", maxInternetUsersCountry, maxLiteracy);

    }

    private static void printMaxMin(String title, Country InternetUsersCountry, Country Literacy) {
        System.out.println();
        System.out.println("Printing "+ title);
        System.out.printf("%10s %12s %12s %5s %12s %n","name","","internet users","","adult literacy");
        System.out.println("--------------------");
        System.out.printf("%10s %8s %8s %n",InternetUsersCountry.getName(),"",InternetUsersCountry.getInternetUsers().setScale(2,RoundingMode.CEILING));
        System.out.printf("%10s %8s %28s %n",Literacy.getName(),"",Literacy.getAdultLiteracyRate().setScale(2,RoundingMode.CEILING));
        System.out.println();
    }


    @SuppressWarnings("unchecked")
    private static List<Country> findAllCountryData(){
        Session session = sessionFactory.openSession();

        //create criteria object
        //Criteria criteria = session.createCriteria(Contact.class);

        //List <Contact> list = criteria.list();


        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery criteriaQuery = builder.createQuery(Country.class);

        criteriaQuery.from(Country.class);

        List <Country> list = session.createQuery(criteriaQuery).getResultList();

        System.out.println(list.size());


        session.close();

        return list;
    }


}
