package ua.sumdu.greenberg.model.objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * This is Product EJB
 *
 * @author GREEN
 *
 */
@Entity
@Table(name = "PRODUCTS")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_PRODUCTS", query = "SELECT * FROM PRODUCTS WHERE IS_ACTIVE = 'active'", resultClass = Product.class)
})
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private int id;
	@Column(name = "SELER_ID")
	private int sellerID;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Column(name = "START_PRICE")
	private int startPrice;
	@Column(name = "BUYOUT_PRICE")
	private int buyoutPrice;
	@Column(name = "CURRENT_PRICE")
	private int currentPrice;
	@Column(name = "CURRENT_BUYER_ID")
	private int currentBuyerID;
	@Column(name = "IS_ACTIVE")
	private String isActive;

	public Product(){}
	public Product(int id, int sellerID, String name, String description,
				   Date startDate, Date endDate, int startPrice, int buyoutPrice,
				   int currentPrice, int currentBuyerID, String isActive) {
		setId(id);
		setSellerID(sellerID);
		setName(name);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setStartPrice(startPrice);
		setBuyoutPrice(buyoutPrice);
		setCurrentPrice(currentPrice);
		setCurrentBuyerID(currentBuyerID);
		setActive(isActive);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getSellerID() {
		return sellerID;
	}


	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public int getBuyoutPrice() {
		return buyoutPrice;
	}


	public void setBuyoutPrice(int buyoutPrice) {
		this.buyoutPrice = buyoutPrice;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getCurrentPrice() {
		return currentPrice;
	}


	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}


	public boolean isActive() {
		return (isActive.equals("active"))? true : false;
	}


	public void setActive(String isActive) {
		this.isActive = isActive;
	}


	public int getCurrentBuyerID() {
		return currentBuyerID;
	}


	public void setCurrentBuyerID(int currentBuyerID) {
		this.currentBuyerID = currentBuyerID;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public int getStartPrice() {
		return startPrice;
	}


	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

}