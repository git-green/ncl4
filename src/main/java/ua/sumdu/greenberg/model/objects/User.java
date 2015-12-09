package ua.sumdu.greenberg.model.objects;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * This is User EJB
 *
 * @author GREEN
 *
 */
@Entity
@Table(name = "USERS")
@NamedNativeQueries({
		@NamedNativeQuery(name="AUTHORIZATION", query="SELECT ID, LOGIN, PASSWORD, NAME, SECOND_NAME, EMAIL, PHONE, STATUS, REGISTRATION_DATE, TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\", IS_ACTIVE, IS_BANED FROM USERS WHERE LOGIN = ? AND PASSWORD = ?", resultClass = User.class),
		@NamedNativeQuery(name="AUTHORIZATION_BY_EMAIL", query="SELECT ID, LOGIN, PASSWORD, NAME, SECOND_NAME, EMAIL, PHONE, STATUS, REGISTRATION_DATE, TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\", IS_ACTIVE, IS_BANED FROM USERS WHERE EMAIL = ? AND PASSWORD = ?", resultClass = User.class)
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private int id;
	@Column(name = "LOGIN")
	private String login;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "NAME")
	private String name;
	@Column(name = "SECOND_NAME")
	private String secondName;
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH")
	private Date birth;
	private int age;
	@Column(name = "EMAIL")
	private String eMail;
	@Column(name = "PHONE")
	private String phone;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "IS_ACTIVE")
	private String active;
	@Column(name = "IS_BANED")
	private String baned;

	private boolean isAdmin;

	private boolean isActivated;

	private boolean isBanned;

	@Temporal(TemporalType.DATE)
	@Column(name = "REGISTRATION_DATE")
	private Date registrationDate;

	public User(){}
	public User(int id, String login, String password, String name,
				String secondName, int age, String eMail, String phone, String status, Date reistrationDate) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
/*		setId(id);
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
*/	}

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
		boolean stAdmin = false;
		if (getStatus().equals("admin")) stAdmin = true;
		else if (getStatus().equals("user")) stAdmin = false;
		return stAdmin;
	}

//	public void setAdmin(boolean isAdmin) {
//		this.isAdmin = isAdmin;
//	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBaned() {
		return baned;
	}

	public void setBaned(String baned) {
		this.baned = baned;
	}

	public boolean isActivated() {
		boolean stActive = false;
		if (getActive().equals("active")) stActive = true;
		else if (getActive().equals("unactivated")) stActive = false;
		return stActive;
	}

//	public void setActivated(boolean isActivated) {
//		this.isActivated = isActivated;
//	}

	public boolean isBanned() {
		boolean stBaned = false;
		if (getBaned().equals("unbanned")) stBaned = false;
		else if (getBaned().equals("baned")) stBaned = true;
		return stBaned;
	}

//	public void setBanned(boolean isBanned) {
//		this.isBanned = isBanned;
//	}

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
