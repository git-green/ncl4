package ua.sumdu.greenberg.model;

import java.util.*;

import ua.sumdu.greenberg.model.objects.Following;
import ua.sumdu.greenberg.model.objects.Product;
import ua.sumdu.greenberg.model.objects.User;

import javax.persistence.EntityManager;

public class ServerTimerTask {

	private static final int DELAY = 300000;//Every 300 seconds
	private static Timer timer = null;
    private static EntityManager ems = EManager.getInstance();
    private EntityManager em = EManager.getInstance();

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
        List<Product> getProducts = (List<Product>) ems.createNamedQuery("GET_ALL_PRODUCTS").getResultList();
        ems.getTransaction().begin();
        for (Product product : getProducts) {
            if (product.getEndDate().getTime() < new Date().getTime()) {
                product.setActive("disactive");
                if (product.getCurrentPrice() != 0) {
                    List<Following> getFollowings = (List<Following>) ems.createNamedQuery("GET_FOLLOWING_BY_ID").setParameter(1, product.getId()).getResultList();
                    for (Following following : getFollowings) {
                        ems.remove(following);
                    }
                    Messager.sendEndAuctionMessage(product.getId());
                }
            }
        }
        ems.getTransaction().commit();
	}
}