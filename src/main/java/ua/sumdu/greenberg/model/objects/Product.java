package ua.sumdu.greenberg.model.objects;

import com.sun.istack.internal.Nullable;

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
		@NamedNativeQuery(name = "GET_ALL_PRODUCTS", query = "SELECT * FROM PRODUCTS WHERE IS_ACTIVE = 'active'", resultClass = Product.class),
		@NamedNativeQuery(name = "GET_COUNT_PRODUCTS_FOR_ALL", query = "SELECT\n" +
																		"    count(0)\n" +
																		"FROM\n" +
																		"  product_category pc,\n" +
																		"  products p,\n" +
																		"  categories c\n" +
																		"WHERE\n" +
																		"  pc.product_id = p.id(+)\n" +
																		"  and pc.product_id = c.id(+) \n" +
																		"  and p.is_active = 'active'\n" +
																		"  and ((p.current_price = 0 and p.start_price >= ?) \n" +
																		"\tor (p.current_price != 0 and p.current_price >= ?))\n" +
																		"  and ((p.current_price = 0 and p.start_price <= ?) \n" +
																		"\tor (p.current_price != 0 and p.current_price <= ?))"),
		@NamedNativeQuery(name = "GET_COUNT_PRODUCTS_FOR_CATEGORY", query = "SELECT\n" +
																			"    count(0)\n" +
																			"FROM\n" +
																			"  product_category pc,\n" +
																			"  products p,\n" +
																			"  categories c\n" +
																			"WHERE\n" +
																			"  pc.product_id = p.id(+)\n" +
																			"  and pc.product_id = c.id(+) \n" +
																			"  and pc.category_id = ? \n" +
																			"  and p.is_active = 'active'\n" +
																			"  and ((p.current_price = 0 and p.start_price >= ? )\n" +
																			"\tor (p.current_price != 0 and p.current_price >= ? ))\n" +
																			"  and ((p.current_price = 0 and p.start_price <= ? ) \n" +
																			"\tor (p.current_price != 0 and p.current_price <= ? ))"),
		@NamedNativeQuery(name = "GET_PRODUCTS_FOR_ALL_CATEGORIES", query ="select\n" +
																			"  *\n" +
																			"from\n" +
																			"  (select\n" +
																			"     g.*,\n" +
																			"     rownum rn\n" +
																			"   from\n" +
																			"     (SELECT\n" +
																			"        p.*\n" +
																			"      FROM\n" +
																			"        product_category pc,\n" +
																			"        products p,\n" +
																			"        categories c\n" +
																			"      WHERE\n" +
																			"        pc.product_id = p.id(+)\n" +
																			"        and pc.product_id = c.id(+)\n" +
																			"        and p.is_active = 'active'\n" +
																			"        and ((p.current_price = 0 and p.start_price >= ? )\n" +
																			"             or (p.current_price != 0 and p.current_price >= ? ))\n" +
																			"        and ((p.current_price = 0 and p.start_price <= ? )\n" +
																			"             or (p.current_price != 0 and p.current_price <= ? )))g)\n" +
																			"where\n" +
																			"  (rn > (?  * 10  - 10) and (rn <= ?  * 10)) ORDER BY ID ASC", resultClass = Product.class),
		@NamedNativeQuery(name = "GET_PRODUCTS_FOR_CATEGORY", query ="select\n" +
																	"  *\n" +
																	"from\n" +
																	"  (select\n" +
																	"     g.*,\n" +
																	"     rownum rn\n" +
																	"   from\n" +
																	"     (SELECT\n" +
																	"        p.*\n" +
																	"      FROM\n" +
																	"        product_category pc,\n" +
																	"        products p,\n" +
																	"        categories c\n" +
																	"      WHERE\n" +
																	"        pc.product_id = p.id(+)\n" +
																	"        and pc.product_id = c.id(+)\n" +
																	"		 and pc.category_id = ? \n" +
																	"        and p.is_active = 'active'\n" +
																	"        and ((p.current_price = 0 and p.start_price >= ? )\n" +
																	"             or (p.current_price != 0 and p.current_price >= ? ))\n" +
																	"        and ((p.current_price = 0 and p.start_price <= ? )\n" +
																	"             or (p.current_price != 0 and p.current_price <= ? )))g)\n" +
																	"where\n" +
																	"  (rn > (? * 10  - 10) and (rn <= ? * 10)) ORDER BY ID ASC", resultClass = Product.class),
		@NamedNativeQuery(name="FIND_COUNT_ALL_PRODUCT", query="SELECT\n" +
														"  count(*)\n" +
														"FROM\n" +
														"  products p\n" +
														"WHERE\n" +
														"  (p.name LIKE (?) /*text*/ or p.name LIKE upper(?) /*text*/\n" +
														"  and p.is_active = 'active'\n" +
														"    or p.description LIKE (?) /*text*/ or p.description LIKE upper(?) /*text*/)\n" +
														"  and ((p.current_price = 0 and p.start_price >= ? /*minPrice*/)\n" +
														"    or (p.current_price != 0 and p.current_price >= ? /*minPrice*/))\n" +
														"  and ((p.current_price = 0 and p.start_price <= ? /*maxPrice*/)\n" +
														"    or (p.current_price != 0 and p.current_price <= ? /*maxPrice*/))"),
		@NamedNativeQuery(name="GET_FIND_PRODUCT_ON_PAGE", query="SELECT\n" +
																"    *\n" +
																"FROM\n" +
																"  ( SELECT\n" +
																"        g.*,\n" +
																"        rownum rn\n" +
																"    FROM\n" +
																"        ( SELECT\n" +
																"              p.*\n" +
																"          FROM\n" +
																"              products p\n" +
																"          WHERE\n" +
																"              (p.name LIKE (?) /*text*/ or p.name LIKE upper(?) /*text*/\n" +
																"                or p.description LIKE (?) /*text*/ or p.description LIKE upper(?) /*text*/)\n" +
																"              and p.is_active = 'active'\n" +
																"              and ((p.current_price = 0 and p.start_price >= ? /*minPrice*/)\n" +
																"                or (p.current_price != 0 and p.current_price >= ? /*minPrice*/))\n" +
																"              and ((p.current_price = 0 and p.start_price <= ? /*maxPrice*/)\n" +
																"                or (p.current_price != 0 and p.current_price <= ? /*maxPrice*/)))g)\n" +
																"WHERE\n" +
																"  (rn > (? /*page*/ * 10  - 10) and (rn <= ? /*page*/ * 10)) ORDER BY ID ASC", resultClass = Product.class),
		@NamedNativeQuery(name="GET_PRODUCT_BY_ID", query="SELECT * FROM products WHERE id = ?", resultClass = Product.class),
		@NamedNativeQuery(name="GET_USER_BUYING", query="SELECT * FROM PRODUCTS WHERE CURRENT_BUYER_ID = ? AND IS_ACTIVE = 'disactive'", resultClass = Product.class),
		@NamedNativeQuery(name="GET_FOLLOWING_PRODUCTS", query="select distinct(p.id), p.seller_id, p.name, p.description, p.start_date, p.end_date, p.start_price, p.buyout_price, p.current_price, p.current_buyer_id, p.is_active\n" +
																"from  products p,\n" +
																"      followings f\n" +
																"where \n" +
																"      p.id = f.product_id \n" +
																"      and f.follower_id= ? \n" +
																"      and p.is_active = 'active'", resultClass = Product.class),
		@NamedNativeQuery(name="GET_USERS_PRODUCTS", query="SELECT * FROM PRODUCTS WHERE SELLER_ID = ?", resultClass = Product.class)
})
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "PRODUCT_ID_S")
	@SequenceGenerator(name = "PRODUCT_ID_S", sequenceName = "PRODUCT_ID_S", allocationSize = 1)
	@Column(name = "ID")
	private int id;
	@Column(name = "SELLER_ID")
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