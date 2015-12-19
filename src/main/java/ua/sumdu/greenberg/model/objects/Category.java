package ua.sumdu.greenberg.model.objects;

import java.io.Serializable;

import javax.persistence.*;

/**
 * This is Category EJB
 *
 * @author GREEN
 *
 */
@Entity
@Table(name = "CATEGORIES")
@NamedNativeQueries({
		@NamedNativeQuery(name="GET_ALL_CATEGORIES", query="SELECT * FROM CATEGORIES", resultClass = Category.class)
})
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private int id;
	@Column(name = "PARENT_ID")
	private int parentID;
	@Column(name = "NAME")
	private String name;


	public Category(){}

	/**
	 * This is constructor for Category
	 *
	 * @param id = id
	 * @param parentID = parentID
	 * @param name = name
	 */
	public Category(int id, int parentID, String name) {
		setId(id);
		setParentID(parentID);
		setName(name);
	}

//	/**
//	 * This is constructor for Category
//	 *
//	 * @param id = id
//	 * @param name = name
//	 */
//	public Category(int id, String name) {
//		setId(id);
//		setParentID(0);
//		setName(name);
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}