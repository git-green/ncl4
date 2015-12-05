package ua.sumdu.greenberg.model;

public class Queries {

    //------------------------------------------------------
    //-----------------------XXX:USER-----------------------
    //------------------------------------------------------
	
	public static final String ADD_USER =
			"INSERT INTO USERS("
					+ "ID,"
					+ "LOGIN,"
					+ "PASSWORD,"
					+ "NAME,"
					+ "SECOND_NAME,"
					+ "BIRTH,"
					+ "EMAIL,"
					+ "PHONE,"
					+ "REGISTRATION_DATE,"
					+ "STATUS)"
			+ "VALUES("
					+ "USER_ID_S.NEXTVAL,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "LOWER(?),"
					+ "?,"
					+ "SYSDATE,"
					+ "?)";
	
	public static final String GET_USER =
			"SELECT"
					+ " LOGIN,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE ID = ?";
	
	public static final String GET_USER_BY_LOGIN =
			"SELECT"
					+ " ID,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE LOGIN = ?";
	
	public static final String IS_LOGIN_FREE =
			"SELECT * FROM USERS WHERE LOGIN = ?";
	
	public static final String IS_EMAIL_FREE =
			"SELECT * FROM USERS WHERE EMAIL = LOWER(?)";
    
	public static final String AUTHORIZATION =
			"SELECT"
					+ " ID,"
					+ " LOGIN,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE LOGIN = ?";
    
	public static final String AUTHORIZATION_BY_EMAIL =
			"SELECT"
					+ " ID,"
					+ " LOGIN,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE EMAIL = LOWER(?)";
    
	public static final String GET_ALL_USERS =
			"SELECT "
			+ " ID,"
			+ " LOGIN,"
			+ " PASSWORD,"
			+ " NAME,"
			+ " SECOND_NAME,"
			+ " EMAIL,"
			+ " PHONE,"
			+ " STATUS,"
			+ " REGISTRATION_DATE,"
			+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS";
	
	public static final String GET_USERS_PRODUCTS =
			"SELECT * FROM PRODUCTS WHERE SELLER_ID = ?";
    
	public static final String IS_USER_ADMIN =
			"SELECT * FROM USERS WHERE ID = ? AND STATUS = 'admin'";
    
	public static final String ACTIVATE_USER =
			"UPDATE USERS SET STATUS = 'user' WHERE LOGIN = ?";
		
	public static final String setUserBanQuery(int num) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE USERS SET STATUS = 'banned' WHERE ID IN ( ");
		
		for (int i = 0; i < num - 1; i++)
			sb.append("?, ");
		sb.append("?");	
		sb.append(") AND STATUS != 'admin' AND STATUS != 'unactivated'");
		return sb.toString();
	}

	public static final String UNBAN_ALL_USERS =
			"UPDATE USERS SET STATUS = 'user' WHERE STATUS = 'banned'";
	
	public static final String CHANGE_PASSWORD =
			"UPDATE USERS SET PASSWORD = ? WHERE ID = ? AND PASSWORD = ?";
	
	public static final String CHANGE_DATA =
			"UPDATE USERS SET NAME = ?, SECOND_NAME = ?, PHONE = ? WHERE ID = ?";
	
	public static final String CHANGE_EMAIL =
			"UPDATE USERS SET EMAIL = LOWER(?) WHERE LOGIN = ?";
	
	public static final String DELETE_UNACTIVATED_USERS = 
			"DELETE FROM USERS WHERE STATUS = 'unactivated' AND"
			+ " ((REGISTRATION_DATE + INTERVAL '24' HOUR) < SYSDATE)";
	
    //------------------------------------------------------
    //----------------XXX:PRODUCT FOLLOWING-----------------
    //------------------------------------------------------
	
	
	public static final String FOLLOW_PRODUCT =
			"INSERT INTO FOLLOWINGS("
					+ "ID,"
					+ "FOLLOWER_ID,"
					+ "PRODUCT_ID)"
			+ " VALUES "
					+ "(FOLLOWING_ID_S.NEXTVAL,"
					+ "?,"
					+ "?)";

	public static final String FINISH_PRODUCT_FOLLOWING =
			"DELETE FROM FOLLOWINGS WHERE PRODUCT_ID = ?";
	
	public static final String GET_FOLLOWING_PRODUCTS =
			"SELECT * FROM PRODUCTS"
			+ " JOIN FOLLOWINGS ON PRODUCTS.ID = FOLLOWINGS.PRODUCT_ID"
			+ " WHERE FOLLOWINGS.FOLLOWER_ID = ?";
    	
	public static final String IS_FOLLOW_QUERY =
			"SELECT * FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";

	
    //------------------------------------------------------
    //---------------------XXX:PRODUCT----------------------
    //------------------------------------------------------
	
	public static final String ADD_PRODUCT =
			"INSERT INTO PRODUCTS("
					+ "ID,"
					+ "SELLER_ID,"
					+ "NAME,"
					+ "DESCRIPTION,"
					+ "START_DATE,"
					+ "END_DATE,"
					+ "START_PRICE,"
					+ "BUYOUT_PRICE,"
					+ "CURRENT_PRICE,"
					+ "CURRENT_BUYER_ID,"
					+ "IS_ACTIVE)"
			+ " VALUES"
					+ "(PRODUCT_ID_S.NEXTVAL,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "SYSDATE,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "0,"
					+ "NULL,"
					+ "'active')";
    
	public static final String GET_PRODUCT =
			"SELECT * FROM PRODUCTS WHERE ID = ?";
    
	public static final String DISACTIVATE_PRODUCT =
			"UPDATE PRODUCTS SET IS_ACTIVE = 'disactive' WHERE ID = ?";
    
	public static final String IS_PRODUCT_ACTIVE =
			"SELECT * FROM PRODUCTS WHERE ID = ? AND IS_ACTIVE = 'active'";
    
	public static final String QET_CURRENT_PRICE =
			"SELECT CURRENT_PRICE FROM PRODUCTS WHERE ID = ?";
    
	public static final String MAKE_A_BET =
			"UPDATE PRODUCTS SET"
					+ " CURRENT_PRICE = ?,"
					+ " CURRENT_BUYER_ID = ?"
			+ " WHERE ID = ?";
	
	public static final String BUYOUT =
			"UPDATE PRODUCTS SET"
					+ " CURRENT_PRICE = BUYOUT_PRICE,"
					+ " CURRENT_BUYER_ID = ?,"
					+ " END_DATE = SYSDATE,"
					+ " IS_ACTIVE = 'disactive'"
			+ " WHERE ID = ?";
    
	public static final String FINISH_AUCTIONS =
			"SELECT * FROM PRODUCTS"
			+ " WHERE IS_ACTIVE = 'active' AND END_DATE < SYSDATE";
    
	public static final String GET_ALL_PRODUCTS =
			"SELECT * FROM PRODUCTS";
	
	public static final String GET_USER_BUYING =
			"SELECT * FROM PRODUCTS WHERE CURRENT_BUYER_ID = ? AND IS_ACTIVE = 'disactive'";
    
	public static final String FIND_PRODUCTS =
			"SELECT * FROM PRODUCTS"
				+ " WHERE "
					+ "LOWER(DESCRIPTION) LIKE LOWER(?)"
				+ " OR "
					+ "LOWER(NAME) LIKE LOWER(?)";
    
	public static final String deleteProductsByIdFromPRODUCTS(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PRODUCTS WHERE ID IN ( ");
		for (int i = 0; i < num - 1; i++)
			sb.append("?, ");
		sb.append("?)");
		return sb.toString();
	}
	
	public static final String deleteProductsByIdFromPICTURES(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PICTURES WHERE PRODUCT_ID IN ( ");
		for (int i = 0; i < num - 1; i++)
			sb.append("?, ");
		sb.append("?)");
		return sb.toString();
	}
	
	public static final String deleteProductsByIdFromFOLLOWINGS(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM FOLLOWINGS WHERE PRODUCT_ID IN ( ");
		for (int i = 0; i < num - 1; i++)
			sb.append("?, ");
		sb.append("?)");
		return sb.toString();
	}
	
	public static final String deleteProductsByIdFromPRODUCT_CATEGORY(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PRODUCT_CATEGORY WHERE PRODUCT_ID IN ( ");
		for (int i = 0; i < num - 1; i++)
			sb.append("?, ");
		sb.append("?)");
		return sb.toString();
	}
	
	public static final String GET_PRODUCT_SELLER =
			"SELECT USERS.* FROM USERS"
			+ " JOIN PRODUCTS ON USERS.ID = PRODUCTS.SELLER_ID"
			+ " WHERE PRODUCTS.ID = ?";
	
	public static final String SELECT_COUNT_FIND =
			"SELECT COUNT(DISTINCT ID) AS COUNT FROM PRODUCTS"
			+ " WHERE IS_ACTIVE = 'active'"
			+ " AND (LOWER(DESCRIPTION) LIKE LOWER(?)"
			+ " OR LOWER(NAME) LIKE LOWER(?))"
			+ " AND ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?))";
	
	public static final String SELECT_COUNT_FIND_WITH_MAX =
			"SELECT COUNT(DISTINCT ID) AS COUNT FROM PRODUCTS"
			+ " WHERE IS_ACTIVE = 'active'"
			+ " AND (LOWER(DESCRIPTION) LIKE LOWER(?)"
			+ " OR LOWER(NAME) LIKE LOWER(?))"
			+ " AND ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?))"
			+ " AND ((START_PRICE <= ? AND CURRENT_PRICE = 0) OR ((CURRENT_PRICE < ?) AND (CURRENT_PRICE != 0)))";
	
	public static final String SELECT_PRODUCTS_FIND = 
			"SELECT * FROM ( SELECT T.*, ROWNUM RN FROM ( SELECT * FROM PRODUCTS "
			+ " WHERE ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?))"
			+ " AND (IS_ACTIVE = 'active')"
			+ " AND ((LOWER(DESCRIPTION) LIKE LOWER(?)) OR (LOWER(NAME) LIKE LOWER(?))) ) T) "
			+ " WHERE (RN > (? * ? - ?) AND (RN <= ? * ?)) ORDER BY ID DESC";
	
	public static final String SELECT_PRODUCTS_FIND_WITH_MAX = 
			"SELECT * FROM ( SELECT T.*, ROWNUM RN FROM ( SELECT * FROM PRODUCTS "
			+ " WHERE ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) "
			+ "   AND ((START_PRICE <= ? AND CURRENT_PRICE = 0) OR ((CURRENT_PRICE < ?) AND (CURRENT_PRICE != 0)))"
			+ " AND (IS_ACTIVE = 'active')"
			+ " AND ((LOWER(DESCRIPTION) LIKE LOWER(?)) OR (LOWER(NAME) LIKE LOWER(?))) ) T) "
			+ " WHERE (RN > (? * ? - ?) AND (RN <= ? * ?)) ORDER BY ID DESC";
		
	public static final String SELECT_PRODUCT_COUNT_WITH_CATEGORY_AND_MAX_PRICE =
			" SELECT COUNT(DISTINCT PRODUCTS.ID) AS COUNT FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID"
			+ " WHERE IS_ACTIVE = 'active' "
			+ " AND CATEGORIES.ID = ? "
			+ " AND ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) "
			+ " AND ((START_PRICE <= ? AND CURRENT_PRICE = 0) OR ((CURRENT_PRICE < ?) AND (CURRENT_PRICE != 0))) ";
	
	public static final String SELECT_PRODUCT_COUNT_WITH_CATEGORY =
			" SELECT COUNT(DISTINCT PRODUCTS.ID) AS COUNT FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID"
			+ " WHERE IS_ACTIVE = 'active' "
			+ " AND CATEGORIES.ID = ? "
			+ " AND ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) ";
	
	public static final String SELECT_PRODUCT_COUNT_WITH_MAX_PRICE =
			" SELECT COUNT(DISTINCT PRODUCTS.ID) AS COUNT FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID"
			+ " WHERE IS_ACTIVE = 'active' "
			+ " AND ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) "
			+ " AND ((START_PRICE <= ? AND CURRENT_PRICE = 0) OR ((CURRENT_PRICE < ?) AND (CURRENT_PRICE != 0)) ";
	
	public static final String SELECT_PRODUCT_COUNT =
			" SELECT COUNT(DISTINCT PRODUCTS.ID) AS COUNT FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID"
			+ " WHERE IS_ACTIVE = 'active' "
			+ " AND ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) ";
	
	public static final String SELECT_PRODUCTS_WITH_MAX_PRICE_AND_CATEGORIES =
			" SELECT * FROM ( SELECT T.*, ROWNUM RN FROM ( "
			+ " SELECT DISTINCT PRODUCTS.* FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID "
			+ " WHERE ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) "
			+ " AND ((START_PRICE <= ? AND CURRENT_PRICE = 0) OR ((CURRENT_PRICE < ?) AND (CURRENT_PRICE != 0))) "
			+ " AND CATEGORIES.ID = ? "
			+ " AND IS_ACTIVE = 'active' ) T) "
			+ " WHERE (RN > (? * ? - ?) AND (RN <= ? * ?)) ORDER BY ID DESC ";
	
	public static final String SELECT_PRODUCTS =
			" SELECT * FROM ( SELECT T.*, ROWNUM RN FROM ( "
			+ " SELECT DISTINCT PRODUCTS.* FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID "
			+ " WHERE ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) "
			+ " AND IS_ACTIVE = 'active' ) T) "
			+ " WHERE (RN > (? * ? - ?) AND (RN <= ? * ?)) ORDER BY ID DESC ";
	
	public static final String SELECT_PRODUCTS_WITH_MAX_PRICE =
			" SELECT * FROM ( SELECT T.*, ROWNUM RN FROM ( "
			+ " SELECT DISTINCT PRODUCTS.* FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID "
			+ " WHERE ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) "
			+ "   AND ((START_PRICE <= ? AND CURRENT_PRICE = 0) OR ((CURRENT_PRICE < ?) AND (CURRENT_PRICE != 0))) "
			+ " AND IS_ACTIVE = 'active' ) T) "
			+ " WHERE (RN > (? * ? - ?) AND (RN <= ? * ?)) ORDER BY ID DESC ";
	
	public static final String SELECT_PRODUCTS_WITH_CATEGORIES =
			" SELECT * FROM ( SELECT T.*, ROWNUM RN FROM ( "
			+ " SELECT DISTINCT PRODUCTS.* FROM PRODUCT_CATEGORY "
			+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID "
			+ " FULL OUTER JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID "
			+ " WHERE ((START_PRICE >= ? AND CURRENT_PRICE = 0) OR (CURRENT_PRICE > ?)) "
			+ " AND CATEGORIES.ID = ? "
			+ " AND IS_ACTIVE = 'active' ) T) "
			+ " WHERE (RN > (? * ? - ?) AND (RN <= ? * ?)) ORDER BY ID DESC ";
	
	
    //------------------------------------------------------
    //--------------------XXX:CATEGORY----------------------
    //------------------------------------------------------
	
	
	public static final String GET_CATEGORY =
			"SELECT * FROM CATEGORIES WHERE ID = ?";
    
	public static final String ADD_CATEGORY =
			"INSERT INTO CATEGORIES("
					+ "ID,"
					+ "PARENT_ID,"
					+ "NAME)"
			+ "VALUES"
					+ "(CATEGORY_ID_S.NEXTVAL,"
					+ "?,"
					+ "?)";
	
	public static final String ADD_ROOT_CATEGORY =
			"INSERT INTO CATEGORIES("
					+ "ID,"
					+ "NAME)"
			+ "VALUES"
					+ "(CATEGORY_ID_S.NEXTVAL,"
					+ "?)";
    
	public static final String GET_ALL_CATEGORIES =
			"SELECT * FROM CATEGORIES";
    
	public static final String GET_PRODUCTS_BY_CATEGORY =
			"SELECT PRODUCTS.* FROM PRODUCT_CATEGORY"
				+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID ="
						+ " PRODUCT_CATEGORY.CATEGORY_ID"
				+ " LEFT JOIN PRODUCTS ON PRODUCTS.ID ="
						+ " PRODUCT_CATEGORY.PRODUCT_ID WHERE CATEGORIES.ID = ?";

	public static final String GET_CATEGORIES_OF_PRODUCT =
			"SELECT CATEGORIES.* FROM PRODUCT_CATEGORY"
				+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID ="
						+ " PRODUCT_CATEGORY.CATEGORY_ID"
				+ " LEFT JOIN PRODUCTS ON PRODUCTS.ID ="
						+ " PRODUCT_CATEGORY.PRODUCT_ID WHERE PRODUCTS.ID = ?";
    
	public static String setCategoriesToProductQuery(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		for (int i = 0; i < num; i++)
			sb.append("INTO PRODUCT_CATEGORY (CATEGORY_ID, PRODUCT_ID) VALUES (?, ?) ");
		sb.append("SELECT * FROM dual");
		return sb.toString();
	}
	
	public static final String CHANGE_CATEGORY =
			"UPDATE CATEGORIES SET NAME = ? WHERE ID = ?";
			
	public static final String deleteCategories(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM CATEGORIES WHERE ID IN ( ");
		for (int i = 0; i < num - 1; i++)
			sb.append("?, ");
		sb.append("?)");
		return sb.toString();
	}
	
	public static final String deleteProductsCategories(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PRODUCT_CATEGORY WHERE CATEGORY_ID IN ( ");
		for (int i = 0; i < num - 1; i++)
			sb.append("?, ");
		sb.append("?)");
		return sb.toString();
	}
	
    //------------------------------------------------------
    //--------------------XXX:PICTURES----------------------
    //------------------------------------------------------
	
	
	public static final String GET_PICTURES_OF_PRODUCT =
			"SELECT * FROM PICTURES WHERE PRODUCT_ID = ?";
    
	public static final String ADD_PICTURE =
			"INSERT INTO PICTURES("
					+ "PRODUCT_ID,"
					+ "URL)"
			+ "VALUES("
					+ "?,"
					+ "?)";
	
	public static final String GET_ALL_PICTURES =
			"SELECT * FROM PICTURES";
	
	public static final String addPicturesToProduct(int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		for (int i = 0; i < num; i++)
			sb.append("INTO PICTURES (PRODUCT_ID, URL) VALUES (?, ?) ");
		sb.append("SELECT * FROM dual");
		return sb.toString();
	}
	
	public static final String PRODUCT_CURVAL
		= "SELECT PRODUCT_ID_S.CURRVAL from dual";
	
}
