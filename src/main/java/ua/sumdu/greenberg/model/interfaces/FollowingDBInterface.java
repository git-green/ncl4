package ua.sumdu.greenberg.model.interfaces;

import java.util.List;

import ua.sumdu.greenberg.model.objects.*;

public interface FollowingDBInterface {


	public boolean followProduct(int followerID, int productID);

	public boolean finishProductFollowings(int productID);
	
	/**
	 * Returns list of products ID, that followed by user.
	 * With this ID's we can restore objects "Product".
	 * */
	public List<Product> getFollowingProducts(int userID);

}
