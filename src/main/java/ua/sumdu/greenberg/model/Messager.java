package ua.sumdu.greenberg.model;

import java.util.Date;

import org.apache.log4j.Logger;

import ua.sumdu.greenberg.model.objects.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Messager {
    static EntityManager em = Persistence.createEntityManagerFactory("JavaAuction").createEntityManager();
	private static final Logger log = Logger.getLogger(Messager.class);


	private static final String VERIFICATION_URL =
			"http://localhost:7701/AuctionEJB/Verification";

	public static void sendEndAuctionMessage(final int productID) {

		log.info("Method sendEndAuctionMessage starts.....");
		new Thread(){
		    public void run(){
				MailSender mailer = MailSender.getInstance();
                Product product = (Product) em.createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, productID).getResultList().get(0);
				if (product.getCurrentPrice() == 0)
					return;
                User buyer = (User) em.createNamedQuery("GET_USER_BY_ID").setParameter(1, product.getCurrentBuyerID()).getResultList().get(0);
                User seller = (User) em.createNamedQuery("GET_USER_BY_ID").setParameter(1, product.getSellerID()).getResultList().get(0);

				String buyerName = buyer.getName();
				String buyerMail = buyer.geteMail();
				String sellerName = seller.getName();
				String sellerMail = seller.geteMail();
				String productName = product.getName();
				int price = product.getCurrentPrice();

				StringBuilder buyerTextSB = new StringBuilder();
				buyerTextSB.append("Hello, " + buyerName +"!\n");
				buyerTextSB.append("You won in auction and bought " + productName + "!\n");
				buyerTextSB.append("Amount of a transaction: " + price + ".\n");
				buyerTextSB.append("Please, contact with seller:\n");
				buyerTextSB.append("Name:" + sellerName + "\n");
				buyerTextSB.append("E-mail:" + sellerMail + "\n\n");
				buyerTextSB.append("This mail was generated automatically, please don't answer on it");

				mailer.send("Auction Lab3: BUY", buyerTextSB.toString(), buyerMail);

				StringBuilder sellerTextSB = new StringBuilder();
				sellerTextSB.append("Hello, " + sellerName +"!\n");
				sellerTextSB.append("You sold " + productName + " on our auction!\n");
				sellerTextSB.append("Amount of a transaction: " + price + ".\n");
				sellerTextSB.append("Please, contact with buyer:\n");
				sellerTextSB.append("Name:" + buyerName + "\n");
				sellerTextSB.append("E-mail:" + buyerMail + "\n\n");
				sellerTextSB.append("This mail was generated automatically, please don't answer on it");

				mailer.send("Auction Lab4: SELL", sellerTextSB.toString(), sellerMail);
		    }
		  }.start();
	}

	public static void sendBetMessage(final int productID) {
		log.info("Method sendEndAuctionMessage starts.....");

		new Thread(){
		    public void run(){
                User user = (User) em.createNamedQuery("GET_PRODUCT_SELLER").setParameter(1, productID).getResultList().get(0);
                Product product = (Product) em.createNamedQuery("GET_PRODUCT_BY_ID").setParameter(1, productID).getResultList().get(0);
				MailSender mailer = MailSender.getInstance();

				StringBuilder sb = new StringBuilder();
				sb.append("Hello, " + user.getName() +"!\n");
				sb.append("Someone made a bet in auction with " + product.getName() + "!\n");
				sb.append("Current price: " + product.getCurrentPrice() + ".\n");
				sb.append("This mail was generated automatically, please don't answer on it");

				mailer.send("Auction Lab4: BUY", sb.toString(), user.geteMail());
		    }
		  }.start();
	}

	public static void registrationMail(
			final String login, final String name, final String mail) {
		log.info("Method registrationMail starts.....");

		new Thread(){
		    public void run(){
				MailSender mailer = MailSender.getInstance();
				StringBuilder textSB = new StringBuilder();

				textSB.append("Hello, " + name +"! <br>");
				textSB.append("You just registred at our auction Lab4!<br><br>");

				textSB.append("<a href=\"" + VERIFICATION_URL + getRegistrationToken(login) +
						"\" target=\"_blank\">Use this link for verifying your account</a>");

				textSB.append("<br><br>Login: " + login + "<br>");
				textSB.append("This mail was generated automatically, please don't answer on it");

				mailer.send("Auction Lab4: REGISTRATION", textSB.toString(), mail);
		    }
		}.start();
	}

	public static boolean changeMail(final String login, final String mail) {
		log.info("Method changeMailLetter starts.....");

        if (!(((Number) em.createNamedQuery("EMAIL_IS_FREE").setParameter(1, mail).getSingleResult()).intValue() == 0)) return false;

		new Thread(){
		    public void run(){
				MailSender mailer = MailSender.getInstance();
				StringBuilder textSB = new StringBuilder();
				textSB.append("Hello, " + login +"! <br>");
				textSB.append("You just changed email of your account in auction Lab3!<br><br>");

				textSB.append("<a href=\"" +
						VERIFICATION_URL +
						getEmailChangesToken(login, mail) +
						"\" target=\"_blank\">Use this link for verifying new email</a>");

				textSB.append("This mail was generated automatically, please don't answer on it");
				mailer.send("Auction Lab4: MAIL CHANGING", textSB.toString(), mail);
		    }
		}.start();
		em.flush();
		return true;
	}

	private static String getRegistrationToken(String login) {
		String loginToken = StringCrypter.getInstance().encrypt(login);
		String regDateToken = StringCrypter.getInstance().encrypt(
				String.valueOf(new Date().getTime()));
		return "?l=" + loginToken + "&d=" + regDateToken;
	}

	private static String getEmailChangesToken(String login, String email) {
		String idToken = StringCrypter.getInstance().encrypt(login);
		String emailToken = StringCrypter.getInstance().encrypt(email);
		return "?l=" + idToken + "&e=" + emailToken;
	}
}
