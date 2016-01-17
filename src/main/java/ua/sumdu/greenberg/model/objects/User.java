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
		@NamedNativeQuery(name="GET_ALL_USERS", query="SELECT * FROM USERS", resultClass = User.class),
		@NamedNativeQuery(name="GET_USER_BY_ID", query="SELECT * FROM USERS WHERE id = ?", resultClass = User.class),
		@NamedNativeQuery(name="AUTHORIZATION", query="SELECT * FROM USERS WHERE LOGIN = ? AND PASSWORD = ?", resultClass = User.class),
		@NamedNativeQuery(name="AUTHORIZATION_BY_EMAIL", query="SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?", resultClass = User.class),
		@NamedNativeQuery(name="LOGIN_IS_FREE", query="SELECT count(0) FROM users WHERE login = ?"),
		@NamedNativeQuery(name="EMAIL_IS_FREE", query="SELECT count(0) FROM users WHERE email = ?"),
		@NamedNativeQuery(name="GET_PRODUCT_SELLER",query="select u.* from users u, products p where u.id = p.SELLER_ID and p.id = ?", resultClass = User.class),
		@NamedNativeQuery(name="GET_USER_BY_LOGIN", query="select * from users where login = ?", resultClass = User.class)
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "USER_ID_S")
	@SequenceGenerator(name = "USER_ID_S", sequenceName = "USER_ID_S", allocationSize = 1)
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
	@Column(name = "EMAIL")
	private String eMail;
	@Column(name = "PHONE")
	private String phone;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REGISTRATION_DATE")
	private Date registrationDate;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "IS_ACTIVE")
	private String active;
	@Column(name = "IS_BANED")
	private String baned;

	public User(){}

	public User(int id, String login, String password, String name, String secondName,
				Date birth, String eMail, String phone, Date registrationDate,
				String status, String active, String baned) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.secondName = secondName;
		this.birth = birth;
		this.eMail = eMail;
		this.phone = phone;
		this.registrationDate = registrationDate;
		this.status = status;
		this.active = active;
		this.baned = baned;
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
		boolean stAdmin = false;
		if (getStatus().equals("admin")) stAdmin = true;
		else if (getStatus().equals("user")) stAdmin = false;
		return stAdmin;
	}

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
		Long t = (new Date().getTime() - getBirth().getTime())/1000/60/60/24/365;
		return t.intValue();
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

	public boolean isBanned() {
		boolean stBaned = false;
		if (getBaned().equals("unbanned")) stBaned = false;
		else if (getBaned().equals("baned")) stBaned = true;
		return stBaned;
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