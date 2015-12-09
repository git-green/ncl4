package ua.sumdu.greenberg.model;

import ua.sumdu.greenberg.model.interfaces.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.sumdu.greenberg.model.objects.*;

public class OracleDataBase implements UserDBInterface, PicturesDBInterface,
        ProductDBInterface, FollowingDBInterface, CategoriesDBInterface {

	private static final Logger log = Logger.getLogger(OracleDataBase.class);
	
    private static OracleDataBase instance;
    private static final long TIME_ZONE_HOURS = -2 * 3600000;
    
    private Connection conn = null;
    
    private OracleDataBase() {}

    
    private void initConnection() {
    	log.info("Method initConnection starts.....");
        if (conn == null) 
            conn = getConnection();
    }
    
    private void closeConnection() {
    	log.info("Method closeConnection starts.....");
    	if (conn != null)
	    	try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				log.error("SQLException in closeConnection()", e);
			}
    }
    
    
    public static synchronized OracleDataBase getInstance() {
    	log.info("Method getInstance starts.....");
        if (instance == null) 
            instance = new OracleDataBase();
        return instance;
    }
    
    private Connection getConnection() {
    	log.info("Method getConnection starts.....");
        Locale.setDefault(Locale.ENGLISH);
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("auction");
            return dataSource.getConnection();
        } catch (NamingException e) {
            log.error("NamingException in getConnection()", e);
        } catch (SQLException e) {
            log.error("SQLException in getConnection()", e);
        }
        return null;
    }
    

    
    //------------------------------------------------------
    //-----------------------XXX:USER-----------------------
    //------------------------------------------------------

    
    /**
     * Creating of a new User.
     * date is long
     * @return false if login or email is not free or happened some error.
     */
    public boolean addUser(String login, String password, String name, String secondName,
                           long birthDate, String eMail, String phone) {
    	log.info("Method addUser starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_USER)) {    		
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, secondName);
            preparedStatement.setTimestamp(5, new Timestamp(birthDate));
            preparedStatement.setString(6, eMail);
            preparedStatement.setString(7, phone);
            preparedStatement.setString(8, "unactivated");
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addUser()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public User getUser(int id) {
    	log.info("Method getUser starts.....");
    	User user = null;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_USER)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            String login = rs.getString("LOGIN");
	            String password = rs.getString("PASSWORD");
	            String name = rs.getString("NAME");
	            String secondName = rs.getString("SECOND_NAME");
	            int age = rs.getInt("AGE");
	            String eMail = rs.getString("EMAIL");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");
	            Date regDate = addTimeZoneDif(rs.getTimestamp("REGISTRATION_DATE"));
	            
	            user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
            }
        } catch (SQLException e) {
            log.error("SQLException in getUser()", e);
        } finally {
        	closeConnection();
        }
        return user;
    }
    
    public User getUser(String login) {
    	log.info("Method getUser starts.....");
    	User user = null;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            int id = rs.getInt("ID");
	            String password = rs.getString("PASSWORD");
	            String name = rs.getString("NAME");
	            String secondName = rs.getString("SECOND_NAME");
	            int age = rs.getInt("AGE");
	            String eMail = rs.getString("EMAIL");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");
	            Date regDate = addTimeZoneDif(rs.getTimestamp("REGISTRATION_DATE"));
	            
	            user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
            }
        } catch (SQLException e) {
            log.error("SQLException in getUser()", e);
        } finally {
        	closeConnection();
        }
        return user;
    }

	public boolean isLoginFree(String login) {
		log.info("Method isLoginFree starts.....");
		boolean result = false;
		initConnection();
	    try (PreparedStatement preparedStatement =
	    		conn.prepareStatement(Queries.IS_LOGIN_FREE)) {	
	    	preparedStatement.setString(1, login);
	    	try(ResultSet rs = preparedStatement.executeQuery()) {
	    		result = !rs.next();
	    	}
	    } catch (SQLException e) {
	        log.error("SQLException in isLoginFree()", e);
	    } finally {
	    	closeConnection();
	    }
	    return result;
	}

    public boolean isEmailFree(String newEmail) {
    	log.info("Method isEmailFree starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_EMAIL_FREE)) {
            preparedStatement.setString(1, newEmail);
            try(ResultSet rs = preparedStatement.executeQuery()) {
            	result = !rs.next();
            }
        } catch (SQLException e) {
            log.error("SQLException in isEmailFree()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }


//    /**
//     * Check login and password and do authorization.
//     *
//     * @return id of authorizated user. If login or password is wrong - method returns -1.
//     */
 /*   public User authorization(String login, String password) {
    	log.info("Method authorization starts.....");
    	User user = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.AUTHORIZATION)) {
            preparedStatement.setString(1, login);
            try(ResultSet rs = preparedStatement.executeQuery()) {
	            if (rs.next()) {
		            int id = rs.getInt("ID");
		            String name = rs.getString("NAME");
		            String secondName = rs.getString("SECOND_NAME");
		            String eMail = rs.getString("EMAIL");
		            String phone = rs.getString("PHONE");
		            String status = rs.getString("STATUS");
		            int age = rs.getInt("AGE");
		            Date regDate = addTimeZoneDif(rs.getTimestamp("REGISTRATION_DATE"));
		            
		            if (rs.getString("PASSWORD").equals(password))
		            		user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
	            }
            }
        } catch (SQLException e) {
            log.error("SQLException in authorization()", e);
        } finally {
        	closeConnection();
        }
        return user;
    }

*/
//    public User authorizationByEmail(String eMail, String password) {
//    	log.info("Method authorizationByEmail starts.....");
//    	User user = null;
//    	initConnection();
//        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.AUTHORIZATION_BY_EMAIL)) {
//        	preparedStatement.setString(1, eMail);
//            try(ResultSet rs = preparedStatement.executeQuery()){
//	            if (rs.next()) {
//		            int id = rs.getInt("ID");
//		            String name = rs.getString("NAME");
//		            String secondName = rs.getString("SECOND_NAME");
//		            String login = rs.getString("LOGIN");
//		            String phone = rs.getString("PHONE");
//		            String status = rs.getString("STATUS");
//		            int age = rs.getInt("AGE");
//		            Date regDate = addTimeZoneDif(rs.getTimestamp("REGISTRATION_DATE"));
//
//		            if (rs.getString("PASSWORD").equals(password))
//		            		user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
//	            }
//            }
//        } catch (SQLException e) {
//            log.error("SQLException in authorizationByEmail()", e);
//        } finally {
//        	closeConnection();
//        }
//        return user;
//    }


    public boolean isAdmin(int userID) {
    	log.info("Method isAdmin starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_USER_ADMIN)){
        	preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()) {
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error("SQLException in isAdmin()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public List<Product> getUsersProducts(int userID) {
    	log.info("Method getUsersProducts starts.....");
        List<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_USERS_PRODUCTS)) {
            preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getUsersProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<Product> getUsersBuying(int userID) {
    	log.info("Method getUsersBuying starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_USER_BUYING)) {
        	preparedStatement.setInt(1, userID);
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
        	}            
        } catch (SQLException e) {
            log.error("SQLException in getUsersBuying()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}

    
	@Override
	public List<User> getAllUsers() {
    	log.info("Method getAllUsers starts.....");
    	List<User> list = new ArrayList<User>();
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_USERS)) {
    		try (ResultSet rs = preparedStatement.executeQuery()) {
    			while (rs.next()) {
    				int id = rs.getInt("ID");
		            String login = rs.getString("LOGIN");
		            String password = rs.getString("PASSWORD");
		            String name = rs.getString("NAME");
		            String secondName = rs.getString("SECOND_NAME");
		            int age = rs.getInt("AGE");
		            String eMail = rs.getString("EMAIL");
		            String phone = rs.getString("PHONE");
		            String status = rs.getString("STATUS");
		            Date regDate = addTimeZoneDif(rs.getTimestamp("REGISTRATION_DATE"));
		            
		            list.add(new User(id, login, password, name, secondName, age, eMail, phone, status, regDate));
    			}
    		}
        } catch (SQLException e) {
            log.error("SQLException in getAllUsers()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}
	
	@Override
	public boolean setUserBan(List<Integer> usersID) {
    	log.info("Method setUserBan starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement =
        		conn.prepareStatement(Queries.setUserBanQuery(usersID.size()))) {
        	int i = 0;
        	for (int id : usersID) 
        		preparedStatement.setInt(++i, id);
        	preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in setUserBan()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
	
	@Override
	public boolean unBanAllUsers() {
    	log.info("Method unBanAllUsers starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.UNBAN_ALL_USERS)) {
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in unBanAllUsers()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}


	@Override
	public boolean activateUser(String login) {
    	log.info("Method activateUser starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ACTIVATE_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in activateUser()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}

	
	@Override
	public boolean removeUnactivatedUsers() {
    	log.info("Method removeUnactivatedUsers starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.DELETE_UNACTIVATED_USERS)) {
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in removeUnactivatedUsers()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}

	@Override
	public boolean changePassword(int userID, String oldPassword, String newPassword) {
	   	log.info("Method changePassword starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.CHANGE_PASSWORD)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userID);
            preparedStatement.setString(3, oldPassword);
            result = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            log.error("SQLException in changePassword()", e);
        } finally {
        	closeConnection();
        }
		return result;
	}

	@Override
	public boolean changeData(int userID, String name, String secondName, String phone) {
	   	log.info("Method changeDate starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.CHANGE_DATA)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, secondName);
            preparedStatement.setString(3, phone);
            preparedStatement.setInt(4, userID);
            result = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            log.error("SQLException in changeDate()", e);
        } finally {
        	closeConnection();
        }
		return result;
	}
	
	@Override
	public boolean changeEMail(String login, String newEmail) {
	   	log.info("Method changePassword starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.CHANGE_EMAIL)) {
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, login);
            result = preparedStatement.executeUpdate() != 0;
            if (result)
            	System.out.println(Queries.CHANGE_EMAIL);
        } catch (SQLException e) {
            log.error("SQLException in changePassword()", e);
        } finally {
        	closeConnection();
        }
		return result;
	}
	
    //------------------------------------------------------
    //--------------------XXX:FOLLOWING---------------------
    //------------------------------------------------------


    public boolean followProduct(int productID, int followerID) {
    	log.info("Method followProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FOLLOW_PRODUCT)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in followProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
	@Override
	public boolean finishProductFollowings(int productID) {
    	log.info("Method finishProductFollowings starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(
    			Queries.FINISH_PRODUCT_FOLLOWING)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in finishProductFollowings()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
	
    /**
     * Returns list of products ID, that followed by user.
     * With this ID's we can restore objects "Product".
     */
    public List<Product> getFollowingProducts(int userID) {
    	log.info("Method getFollowingProductsID starts.....");
        List<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_FOLLOWING_PRODUCTS)) {
            preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	while (rs.next()) 
	            	list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getFollowingProductsID()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	public boolean isFollowProduct(int followerID, int productID) {
		boolean result = false;
		try(Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(Queries.IS_FOLLOW_QUERY);
			preparedStatement.setInt(1, followerID);
			preparedStatement.setInt(2, productID);
			ResultSet rs = preparedStatement.executeQuery();
			result = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


    //------------------------------------------------------
    //---------------------XXX:PRODUCT----------------------
    //------------------------------------------------------

    
	public synchronized int addProduct(int sellerID, String name,
			String description, long endDate, int startPrice, int buyoutPrice) {
		log.info("Method addProduct starts.....");
		int result = -1;
		if (addProductIntoBD(sellerID, name, description, endDate, startPrice, buyoutPrice)) {
	    	initConnection();
	        try (PreparedStatement preparedStatement = 
	        		conn.prepareStatement(Queries.PRODUCT_CURVAL)) {
	            try(ResultSet rs = preparedStatement.executeQuery()) {
		            rs.next();
		            result = rs.getInt("CURRVAL");
	            }
	        } catch (SQLException e) {
	            log.error("SQLException in addProduct()", e);
	        } finally {
	        	closeConnection();
	        }
		}
		return result;
	}
    
    private boolean addProductIntoBD(int sellerID, String name, String description,
                              long endDate, int startPrice, int buyoutPrice) {
    	log.info("Method addProduct starts.....");
    	boolean result = false;
    	initConnection();
    	try (
        	PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_PRODUCT);
        ) {
            preparedStatement.setInt(1, sellerID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setTimestamp(4, new Timestamp(endDate));
            preparedStatement.setInt(5, startPrice);
            preparedStatement.setInt(6, buyoutPrice);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public Product getProduct(int id) {
    	log.info("Method getProduct starts.....");
    	Product product = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PRODUCT)) {
            preparedStatement.setLong(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            rs.next();
	
	            int sellerID = rs.getInt("SELLER_ID");
	            String name = rs.getString("NAME");
	            String description = rs.getString("DESCRIPTION");
	            Date startDate = addTimeZoneDif(rs.getTimestamp("START_DATE"));
	            Date endDate = addTimeZoneDif(rs.getTimestamp("END_DATE"));
	            int startPrice = rs.getInt("START_PRICE");
	            int buyoutPrice = rs.getInt("BUYOUT_PRICE");
	            int currentPrice = rs.getInt("CURRENT_PRICE");
	            int currentBuyerID = rs.getInt("CURRENT_BUYER_ID");
	            String activity = rs.getString("IS_ACTIVE");
	
	            product =  new Product(id, sellerID, name, description,
	                    startDate, endDate, startPrice, buyoutPrice,
	                    currentPrice, currentBuyerID, activity.equals("active"));
            }
        } catch (SQLException e) {
            log.error("SQLException in getProduct()", e);
        } finally {
        	closeConnection();
        }
        return product;
    }
    
    private static Date addTimeZoneDif(Date date) {
    	return new Date(date.getTime() + TIME_ZONE_HOURS);
    }

    public boolean disactivateProduct(int productID) {
    	log.info("Method disactivateProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.DISACTIVATE_PRODUCT)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in disactivateProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public boolean isProductActive(int productID) {
    	log.info("Method isProductActive starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_PRODUCT_ACTIVE)) {
            preparedStatement.setInt(1, productID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error("SQLException in isProductActive()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public int getCurrentPrice(int productID) {
    	log.info("Method getCurrentPrice starts.....");
    	initConnection();
    	int result = -1;
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.QET_CURRENT_PRICE)) {
            preparedStatement.setLong(1, productID);
            try(ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            result = rs.getInt("CURRENT_PRICE");
            }
        } catch (SQLException e) {
            log.error("SQLException in getCurrentPrice()", e);
        } finally {
        	closeConnection();
        }
        return result; 
    }


    public boolean makeBet(int productID, int buyerID, int price) {
    	log.info("Method makeBet starts.....");
    	Product product = getProduct(productID);
    	
    	if ((product.getCurrentPrice() > price) || (!product.isActive()))
    		return false;
    	
    	if (price >= product.getBuyoutPrice()) 
    		return buyout(productID, buyerID);
    	
    	boolean result = bet(productID, buyerID, price);
    	if (result && !isFollowProduct(buyerID, productID)) {
    		Messager.sendBetMessage(productID);
    		followProduct(productID, buyerID);
    	}
        return result;
    }
    
    private boolean bet(int productID, int buyerID, int price) {
    	log.info("Method makeBet starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.MAKE_A_BET)) {
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, buyerID);
            preparedStatement.setInt(3, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in makeBet()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    private boolean buyoutProduct(int productID, int buyerID) {
    	log.info("Method makeBet starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.BUYOUT)) {
            preparedStatement.setInt(1, buyerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in makeBet()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public boolean buyout(int productID, int buyerID) {
    	if (getProduct(productID).isActive())
	    	if (buyoutProduct(productID, buyerID))
	    		if (finishProductFollowings(productID)) {
	    			Messager.sendEndAuctionMessage(productID);
	    			return true;
	    		}
    	return false;
    }

    /**
     * @return list of products id, that was finished just now
     */
    public ArrayList<Product> finishAuctions() {
    	log.info("Method finishAuctions starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FINISH_AUCTIONS)) {
            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	            	list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
            }
        } catch (SQLException e) {
            log.error("SQLException in finishAuctions()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
    public List<Product> getAllProducts() {
    	log.info("Method getAllProducts starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_PRODUCTS)) {
           
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
        	}            
        } catch (SQLException e) {
            log.error("SQLException in getAllProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<Product> getAllActiveProducts() {
    	log.info("Method getAllActiveProducts starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_PRODUCTS)) {
           
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                if (rs.getString("IS_ACTIVE").equals("active"))
	            	list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				true));
	            }
        	}            
        } catch (SQLException e) {
            log.error("SQLException in getAllActiveProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}
    
	@Override
	public List<Product> findProducts(String substring) {
    	log.info("Method findProducts starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FIND_PRODUCTS)) {
        	preparedStatement.setString(1, "%" + substring + "%");
        	preparedStatement.setString(2, "%" + substring + "%");
            
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
        	}
        } catch (SQLException e) {
            log.error("SQLException in findProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
	
	public User getProductSeller(int productID) {
	   	log.info("Method getProductSeller starts.....");
    	User user = null;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PRODUCT_SELLER)) {
            preparedStatement.setInt(1, productID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            int id = rs.getInt("ID");
	            String login = rs.getString("LOGIN");
	            String password = rs.getString("PASSWORD");
	            String name = rs.getString("NAME");
	            String secondName = rs.getString("SECOND_NAME");
	            String eMail = rs.getString("EMAIL");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");
	            Date regDate = rs.getTimestamp("REGISTRATION_DATE");
	            
	            user = new User(id, login, password, name, secondName, 0, eMail, phone, status, regDate);
            }
        } catch (SQLException e) {
            log.error("SQLException in getProductSeller()", e);
        } finally {
        	closeConnection();
        }
        return user;
	}
	
	@Override
	public boolean deleteProducts(List<Integer> productsID) {
		this.deleteProductsFOLLOWINGS(productsID);
		this.deleteProductsPICTURES(productsID);
		this.deleteProductsPRODUCT_CATEGORY(productsID);
		return this.deleteProductsPRODUCTS(productsID);
	}
	
	private boolean deleteProductsPRODUCTS(List<Integer> productsID) {
    	log.info("Method deleteProductsPRODUCTS starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(
        		Queries.deleteProductsByIdFromPRODUCTS(productsID.size()))) {
            int i = 0;
        	for (int id : productsID)
        		preparedStatement.setInt(++i, id);
            int res = preparedStatement.executeUpdate();
            result = res != 0;
        } catch (SQLException e) {
            log.error("SQLException in deleteProductsPRODUCTS()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
	
	private void deleteProductsPRODUCT_CATEGORY(List<Integer> productsID) {
    	log.info("Method deleteProductsPRODUCT_CATEGORY starts.....");
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(
        		Queries.deleteProductsByIdFromPRODUCT_CATEGORY(productsID.size()))) {
            int i = 0;
        	for (int id : productsID)
        		preparedStatement.setInt(++i, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException in deleteProductsPRODUCT_CATEGORY()", e);
        } finally {
        	closeConnection();
        }
	}
	
	private void deleteProductsPICTURES(List<Integer> productsID) {
    	log.info("Method deleteProductsPICTURES starts.....");
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(
        		Queries.deleteProductsByIdFromPICTURES(productsID.size()))) {
            int i = 0;
        	for (int id : productsID)
        		preparedStatement.setInt(++i, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException in deleteProductsPICTURES()", e);
        } finally {
        	closeConnection();
        }
	}
	
	private void deleteProductsFOLLOWINGS(List<Integer> productsID) {
    	log.info("Method deleteProductsFOLLOWINGS starts.....");
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(
        		Queries.deleteProductsByIdFromFOLLOWINGS(productsID.size()))) {
            int i = 0;
        	for (int id : productsID)
        		preparedStatement.setInt(++i, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException in deleteProductsFOLLOWINGS()", e);
        } finally {
        	closeConnection();
        }
	}
	
	@Override
	public List<Product> getProducts(int postiton, int categoryID, int minPrice, int maxPrice) {
    	log.info("Method getProducts starts.....");
    	List<Product> list = new ArrayList<Product>();
    	initConnection();
    	
    	String query;
    	
    	if ((categoryID != 0) && (maxPrice != 0)) {
    		query = Queries.SELECT_PRODUCTS_WITH_MAX_PRICE_AND_CATEGORIES;
    	} else if ((categoryID == 0) && (maxPrice != 0)) {
    		query = Queries.SELECT_PRODUCTS_WITH_MAX_PRICE;
    	} else if ((categoryID != 0) && (maxPrice == 0)) {
    		query = Queries.SELECT_PRODUCTS_WITH_CATEGORIES;
    	} else {
    		query = Queries.SELECT_PRODUCTS;
    	}
    	
        try (PreparedStatement preparedStatement = 
        		conn.prepareStatement(query)) {
        	
        	if ((categoryID != 0) && (maxPrice != 0)) {
        		preparedStatement.setInt(1, minPrice);
        		preparedStatement.setInt(2, minPrice);
        		preparedStatement.setInt(3, maxPrice);
        		preparedStatement.setInt(4, maxPrice);
        		preparedStatement.setInt(5, categoryID);
        		preparedStatement.setInt(6, LOTS_ON_PAGE);
        		preparedStatement.setInt(7, postiton);
        		preparedStatement.setInt(8, LOTS_ON_PAGE);
        		preparedStatement.setInt(9, postiton);
        		preparedStatement.setInt(10, LOTS_ON_PAGE);
        	} else if ((categoryID == 0) && (maxPrice != 0)) {
        		preparedStatement.setInt(1, minPrice);
        		preparedStatement.setInt(2, minPrice);
        		preparedStatement.setInt(3, maxPrice);
        		preparedStatement.setInt(4, maxPrice);
        		preparedStatement.setInt(5, LOTS_ON_PAGE);
        		preparedStatement.setInt(6, postiton);
        		preparedStatement.setInt(7, LOTS_ON_PAGE);
        		preparedStatement.setInt(8, postiton);
        		preparedStatement.setInt(9, LOTS_ON_PAGE);
        	} else if ((categoryID != 0) && (maxPrice == 0)) {
        		preparedStatement.setInt(1, minPrice);
        		preparedStatement.setInt(2, minPrice);
        		preparedStatement.setInt(3, categoryID);
        		preparedStatement.setInt(4, LOTS_ON_PAGE);
        		preparedStatement.setInt(5, postiton);
        		preparedStatement.setInt(6, LOTS_ON_PAGE);
        		preparedStatement.setInt(7, postiton);
        		preparedStatement.setInt(8, LOTS_ON_PAGE);
        	} else {
        		preparedStatement.setInt(1, minPrice);
        		preparedStatement.setInt(2, minPrice);
        		preparedStatement.setInt(3, LOTS_ON_PAGE);
        		preparedStatement.setInt(4, postiton);
        		preparedStatement.setInt(5, LOTS_ON_PAGE);
        		preparedStatement.setInt(6, postiton);
        		preparedStatement.setInt(7, LOTS_ON_PAGE);
        	}
        	
            try(ResultSet rs = preparedStatement.executeQuery()){

	            while (rs.next()) {
	            	list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				true)//is active
	                		);
	            }
            	
            }
        } catch (SQLException e) {
            log.error("SQLException in getProducts()", e);
        } finally {
        	closeConnection();
        }
        log.debug("IN: postiton = " + postiton + ","
        		+ "categoryID = " + categoryID + ","
        		+ " minPrice = " + minPrice + ","
        		+ " maxPrice = " + maxPrice + "."
        		+ "OUT: list size = " + list.size());
        return list;
	}

	/**
	 * categoryID: 0 if without categories;
	 * minPrice: 0 if without minimal price;
	 * maxPrice: 0 for infinity.
	 * */
	@Override
	public int getCount(int categoryID, int minPrice, int maxPrice) {
    	log.info("Method getCount starts.....");
    	int count = -1;
    	initConnection();
    	
    	String query;
    	
    	if ((categoryID != 0) && (maxPrice != 0)) {
    		query = Queries.SELECT_PRODUCT_COUNT_WITH_CATEGORY_AND_MAX_PRICE;
    	} else if ((categoryID == 0) && (maxPrice != 0)) {
    		query = Queries.SELECT_PRODUCT_COUNT_WITH_MAX_PRICE;
    	} else if ((categoryID != 0) && (maxPrice == 0)) {
    		query = Queries.SELECT_PRODUCT_COUNT_WITH_CATEGORY;
    	} else {
    		query = Queries.SELECT_PRODUCT_COUNT;
    	}
    	
        try (PreparedStatement preparedStatement =
        		conn.prepareStatement(query)) {
        	
        	if ((categoryID != 0) && (maxPrice != 0)) {
        		preparedStatement.setInt(1, categoryID);
        		preparedStatement.setInt(2, minPrice);
        		preparedStatement.setInt(3, minPrice);
        		preparedStatement.setInt(4, maxPrice);
        		preparedStatement.setInt(5, maxPrice);
        	} else if ((categoryID == 0) && (maxPrice != 0)) {
        		preparedStatement.setInt(1, minPrice);
        		preparedStatement.setInt(2, minPrice);
        		preparedStatement.setInt(3, maxPrice);
        		preparedStatement.setInt(4, maxPrice);
        	} else if ((categoryID != 0) && (maxPrice == 0)) {
        		preparedStatement.setInt(1, categoryID);
        		preparedStatement.setInt(2, minPrice);
        		preparedStatement.setInt(3, minPrice);
        	} else {
        		preparedStatement.setInt(1, minPrice);
        		preparedStatement.setInt(2, minPrice);
        	}
        	
            try(ResultSet rs = preparedStatement.executeQuery()){
	            rs.next();
	            count = rs.getInt("COUNT");
            }
        } catch (SQLException e) {
            log.error("SQLException in getCount()", e);
        } finally {
        	closeConnection();
        }
        log.debug("IN: categoryID = " + categoryID +","
        		+ " minPrice = " + minPrice + ","
        		+ " maxPrice = " + maxPrice + "."
        		+ "OUT: count = " + count);
        return count;
	}
	
		/**
	 * position: start with 1. Every page has LOTS_ON_PAGE lots
	 * minPrice: 0 if without minimal price;
	 * maxPrice: 0 for infinity;
	 * keyWord: for search.
	 * */
@Override
	public List<Product> getProductsFind(int postiton, int minPrice, int maxPrice, String keyWord) {
    	log.info("Method getProductsFind starts.....");
    	List<Product> list = new ArrayList<Product>();
    	initConnection();
    	
    	String query;
    	if (maxPrice == 0) {
    		query = Queries.SELECT_PRODUCTS_FIND;
    	} else {
    		query = Queries.SELECT_PRODUCTS_FIND_WITH_MAX;
    	}
    	
        try (PreparedStatement preparedStatement =
        		conn.prepareStatement(query)) {
        	
        	if (maxPrice == 0) {
            	preparedStatement.setInt(1, minPrice);
            	preparedStatement.setInt(2, minPrice);
            	preparedStatement.setString(3, "%" + keyWord + "%");
            	preparedStatement.setString(4, "%" + keyWord + "%");
        		preparedStatement.setInt(5, LOTS_ON_PAGE);
        		preparedStatement.setInt(6, postiton);
        		preparedStatement.setInt(7, LOTS_ON_PAGE);
        		preparedStatement.setInt(8, postiton);
        		preparedStatement.setInt(9, LOTS_ON_PAGE);
        	} else {
            	preparedStatement.setInt(1, minPrice);
            	preparedStatement.setInt(2, minPrice);
            	preparedStatement.setInt(3, maxPrice);
            	preparedStatement.setInt(4, maxPrice);
            	preparedStatement.setString(5, "%" + keyWord + "%");
            	preparedStatement.setString(6, "%" + keyWord + "%");
        		preparedStatement.setInt(7, LOTS_ON_PAGE);
        		preparedStatement.setInt(8, postiton);
        		preparedStatement.setInt(9, LOTS_ON_PAGE);
        		preparedStatement.setInt(10, postiton);
        		preparedStatement.setInt(11, LOTS_ON_PAGE);
        	}
        	
            try(ResultSet rs = preparedStatement.executeQuery()){

	            while (rs.next()) {
	            	list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				true)//is active
	                		);
	            }
            	
            }
        } catch (SQLException e) {
            log.error("SQLException in getProductsFind()", e);
        } finally {
        	closeConnection();
        }
        log.debug("IN: postiton = " + postiton + ","
        		+ " keyWord = " + keyWord + ","
        		+ " minPrice = " + minPrice + ","
        		+ " maxPrice = " + maxPrice + "."
        		+ "OUT: list size = " + list.size());
        return list;
	}

	/**
	 * minPrice: 0 if without minimal price;
	 * maxPrice: 0 for infinity;
	 * keyWord: for search.
	 * */
	@Override
	public int getCountFind(int minPrice, int maxPrice, String keyWord) {
    	log.info("Method getCountFind starts.....");
    	int count = -1;
    	initConnection();
    	
    	String query;
    	if (maxPrice == 0) {
    		query = Queries.SELECT_COUNT_FIND;
    	} else {
    		query = Queries.SELECT_COUNT_FIND_WITH_MAX;
    	}
        try (PreparedStatement preparedStatement = 
        		conn.prepareStatement(query)) {
        	
        	if (maxPrice == 0) {
            	preparedStatement.setString(1, "%" + keyWord + "%");
            	preparedStatement.setString(2, "%" + keyWord + "%");
            	preparedStatement.setInt(3, minPrice);
            	preparedStatement.setInt(4, minPrice);
        	} else {
            	preparedStatement.setString(1, "%" + keyWord + "%");
            	preparedStatement.setString(2, "%" + keyWord + "%");
            	preparedStatement.setInt(3, minPrice);
            	preparedStatement.setInt(4, minPrice);
            	preparedStatement.setInt(5, maxPrice);
            	preparedStatement.setInt(6, maxPrice);
        	}
        	
            try(ResultSet rs = preparedStatement.executeQuery()){
	            rs.next();
	            count = rs.getInt("COUNT");
            }
        } catch (SQLException e) {
            log.error("SQLException in getCountFind()", e);
        } finally {
        	closeConnection();
        }
        
        log.debug("IN: "
        		+ " keyWord = " + keyWord + ","
        		+ " minPrice = " + minPrice + ","
        		+ " maxPrice = " + maxPrice + "."
        		+ "OUT: count = " + count);
        return count;
	}


    //------------------------------------------------------
    //--------------------XXX:CATEGORY----------------------
    //------------------------------------------------------

    public Category getCategory(int categoryID) {
    	log.info("Method getCategory starts.....");
    	Category category = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_CATEGORY)) {
            preparedStatement.setLong(1, categoryID);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            rs.next();
	            String name = rs.getString("NAME");
	            int parentID = rs.getInt("PARENT_ID");
	            category = new Category(categoryID, parentID, name);
            }
        } catch (SQLException e) {
            log.error("SQLException in getCategory()", e);
        } finally {
        	closeConnection();
        }
        return category;
    }

    public boolean addCategory(int parentID, String name) {
    	log.info("Method addCategory(int, String) starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_CATEGORY)) {
            preparedStatement.setInt(1, parentID);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addCategory()", e);
        } finally {
        	closeConnection();
        }      
        return result;
    }

    public boolean addCategory(String name) {
    	log.info("Method addCategory(String) starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_ROOT_CATEGORY)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addCategory(int, String)", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public List<Category> getAllCategories() {
    	log.info("Method getAllCategories starts.....");
        List<Category> list = new ArrayList<Category>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_CATEGORIES)) {
        	 try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	        		list.add(
	                		new Category(
	                				rs.getInt("ID"),
	                				rs.getInt("PARENT_ID"),
	                				rs.getString("NAME")));
        	 }
        } catch (SQLException e) {
            log.error("SQLException in getAllCategories()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<Category> getCategoriesOfProduct(int productID) {
    	log.info("Method getAllCategories starts.....");
        List<Category> list = new ArrayList<Category>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_CATEGORIES_OF_PRODUCT)) {
        	preparedStatement.setInt(1, productID);

            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	        		list.add(
	                		new Category(
	                				rs.getInt("ID"),
	                				rs.getInt("PARENT_ID"),
	                				rs.getString("NAME")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getAllCategories()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}

	@Override
	public List<Product> getProductsByCategory(int categoryID) {
    	log.info("Method getProductsByCategory starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PRODUCTS_BY_CATEGORY)) {	
        	preparedStatement.setInt(1, categoryID);
            
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				addTimeZoneDif(rs.getTimestamp("START_DATE")),
	                				addTimeZoneDif(rs.getTimestamp("END_DATE")),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
            }
        } catch (SQLException e) {
            log.error("SQLException in getProductsByCategory()", e);
        } finally {
        	closeConnection();
        }
		return list;
	}
	


	public boolean addCategoriesToProduct(int productID, List<Integer> categories) {
    	log.info("Method addCategoriesToProduct starts.....");
    	boolean result = false;
    	if (categories.isEmpty())
    		return true;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(
    			Queries.setCategoriesToProductQuery(categories.size()))) {    		
            
    		for (int i = 0; i < categories.size(); i++) {
    			preparedStatement.setInt(2 * i + 1, categories.get(i));
    			preparedStatement.setInt(2 * i + 2, productID);
    		}
    		
    		preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addCategoriesToProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
	
	@Override
	public boolean changeCategory(int categoriesID, String newName) {
    	log.info("Method changeCategory starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement =
        		conn.prepareStatement(Queries.CHANGE_CATEGORY)) {
        	preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, categoriesID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in changeCategory()", e);
        } finally {
        	closeConnection();
        }
        return result;
		
	}
	
	@Override
	public boolean deleteCategory(int categoryID, List<Category> list) {
		List<Integer> deleteList = new ArrayList<Integer>();
		for (Category category : list) 
			if (CategoriesTree.isCategoryChild(category, categoryID, list))
				deleteList.add(category.getId());
		deleteList.add(categoryID);
		if (deleteCategoriesListProducts(deleteList))
			if (deleteCategoriesList(deleteList))
				return true;
		return false;
	}
	
	private boolean deleteCategoriesList(List<Integer> list) {
		log.info("Method deleteCategoryFromCategories starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement =
        		conn.prepareStatement(Queries.deleteCategories(list.size()))) {
            int i = 0;
        	for (int id : list)
        		preparedStatement.setInt(++i, id);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in deleteCategoryFromCategories()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
	
	private boolean deleteCategoriesListProducts(List<Integer> list) {
		log.info("Method deleteCategoryFromCategories starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(
        		Queries.deleteProductsCategories(list.size()))) {
            int i = 0;
        	for (int id : list)
        		preparedStatement.setInt(++i, id);
        	preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in deleteCategoryFromCategories()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
	


    //------------------------------------------------------
    //--------------------XXX:PICTURES----------------------
    //------------------------------------------------------


    public List<Picture> getPictures(int productID) {
    	log.info("Method getPictures starts.....");
        List<Picture> list = new ArrayList<Picture>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PICTURES_OF_PRODUCT)) {
            preparedStatement.setInt(1, productID);

            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	            	list.add(new Picture(
	            			rs.getInt("PRODUCT_ID"),
	            			rs.getString("URL")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getPictures()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }

	@Override
	public List<Picture> getAllPictures() {
    	log.info("Method getAllPictures starts.....");
        List<Picture> list = new ArrayList<Picture>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_PICTURES)) {
            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	            	list.add(new Picture(
	            			rs.getInt("PRODUCT_ID"),
	            			rs.getString("URL")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getAllPictures()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}
	
	public boolean addPicturesToProduct(int productID, List<String> picturesURL) {
    	log.info("Method addPicturesToProduct starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement =
    			conn.prepareStatement(Queries.addPicturesToProduct(picturesURL.size()))) {    		
            
    		for (int i = 0; i < picturesURL.size(); i++) {
    			preparedStatement.setInt(2 * i + 1, productID);
    			preparedStatement.setString(2 * i + 2, picturesURL.get(i));
    		}
    		
    		preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addPicturesToProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
}
