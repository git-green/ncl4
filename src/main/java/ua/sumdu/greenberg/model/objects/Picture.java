package ua.sumdu.greenberg.model.objects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This is EJB
 *
 * @author GREEN
 *
 */
@Entity
@Table(name = "PICTURES")
public class Picture implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name = "PRODUCT_ID")
	private int productID;
	@Column(name = "NAME")
	private String URL;

	/**
	 * This is constructor for Picture
	 *
	 * @param productID = productID
	 * @param URL = picture URL
	 */
	public Picture(int productID, String URL) {
		this.setProductID(productID);
		this.setURL(URL);
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

}
