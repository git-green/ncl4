package ua.sumdu.greenberg.model.objects;

import java.util.Date;
import java.util.List;


public class User {
	
	  private int id;
	  
	  private String login;
	  private String password;
	  private String name;
	  private String secondName;
	  private int age;
	  private String eMail;
	  private String phone;
	  private boolean isAdmin;
	  private boolean isActivated;
	  private boolean isBanned;
	  private Date registrationDate;
		  
	  public User(int id, String login, String password, String name,
			  String secondName, int age, String eMail, String phone, String status, Date reistrationDate) {
		  setId(id);
		  setLogin(login);
		  setPassword(password);
		  setName(name);
		  setSecondName(secondName);
		  setAge(age);
		  setPhone(phone);
		  setRegistrationDate(reistrationDate);
		  seteMail(eMail);
		  switch (status) {
		  	case "admin" :
		  		setActivated(true);
		  		setBanned(false);
		  		setAdmin(true);
		  		break;
		  	case "banned" :
		  		setActivated(true);
		  		setBanned(true);
		  		setAdmin(false);
		  		break;
		  	case "unactivated" :
		  		setActivated(false);
		  		setBanned(false);
		  		setAdmin(false);
		  		break;
		  	default ://"user"
		  		setActivated(true);
		  		setBanned(false);
		  		setAdmin(false);
		  }
	  }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public static User getUserByID(List<User> list, int ID) {
		for (User user : list)
			if (user.getId() == ID)
				return user;
		return null;
 	}
}
