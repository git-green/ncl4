package ua.sumdu.greenberg.model;

import java.util.*;

import ua.sumdu.greenberg.model.objects.Following;
import ua.sumdu.greenberg.model.objects.Product;
import ua.sumdu.greenberg.model.objects.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ServerTimerTask {

	private static final int DELAY = 300000;//Every 300 seconds
	private static Timer timer = null;

	private static ServerTimerTask instance;

	public static void run() {
		if (timer == null)
			timer = new Timer();
		if (instance == null)
			instance = new ServerTimerTask();
	}

	private ServerTimerTask() {
        TimerTask st = new TimerTask() {
			@Override
			public void run() {
		    	finishAuctions();
                EntityManager em = Persistence.createEntityManagerFactory("JavaAuction").createEntityManager();
                List<User> users = (List<User>) em.createNamedQuery("GET_ALL_USERS").getResultList();
                if (!users.isEmpty()) {
                    for (User user : users) {
                        if (user.getActive().equals("unactivated") &&  user.getRegistrationDate().getTime() * 1000L * 60 * 60 * 24 < new Date().getTime()) {
                            em.getTransaction().begin();
                            em.remove(user);
                            em.getTransaction().commit();
                        }
                    }
                }
                em.close();
			}
		};
		timer.schedule(st, DELAY, DELAY);
	}

	public static void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private static void finishAuctions() {
        EntityManager em = Persistence.createEntityManagerFactory("JavaAuction").createEntityManager();
        List<Product> getProducts = (List<Product>) em.createNamedQuery("GET_ALL_PRODUCTS").getResultList();
        em.getTransaction().begin();
        for (Product product : getProducts) {
            if (product.getEndDate().getTime() < new Date().getTime()) {
                product.setActive("disactive");
                if (product.getCurrentPrice() != 0) {
                    List<Following> getFollowings = (List<Following>) em.createNamedQuery("GET_FOLLOWING_BY_ID").setParameter(1, product.getId()).getResultList();
                    for (Following following : getFollowings) {
                        em.remove(following);
                    }
                    Messager.sendEndAuctionMessage(product.getId());
                }
            }
        }
        em.getTransaction().commit();
        em.close();
	}
}