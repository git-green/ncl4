package ua.sumdu.greenberg.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class EManager {
    private static EntityManagerFactory emf;
    private static EntityManager instance;

    private EManager() {}

    public static EntityManagerFactory getEmf() {
        if (emf != null) return emf;
        else {
            emf = Persistence.createEntityManagerFactory("JavaAuction");
        }
        return emf;
    }

    public static EntityManager getInstance() {
        if (instance != null) return instance;
        else {
            instance = getEmf().createEntityManager();
        }
        return instance;
    }


}
