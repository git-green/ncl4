package ua.sumdu.greenberg.model.objects;

import java.io.Serializable;

import javax.persistence.*;

/**
 * This is EJB
 *
 * @author GREEN
 *
 */
@Entity
@Table(name = "PICTURES")
@NamedNativeQueries({
		@NamedNativeQuery(name="GET_ALL_PICTURES", query="SELECT * FROM PICTURES", resultClass = Picture.class)
})
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PRODUCT_ID")
	private int productID;
	@Column(name = "URL")
	private String URL;

	public Picture(){}

	/**
	 * This is constructor for Picture
	 *
	 * @param productID = productID
	 * @param URL = picture URL
	 */
	public Picture(int productID, String URL) {
		this.productID = productID;
		this.URL = URL;
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
		this.URL = uRL;
	}

}
