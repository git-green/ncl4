package ua.sumdu.greenberg.model.objects;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by GREEN on 02.01.2016.
 */
@Entity
@Table(name = "FOLLOWINGS")
@NamedNativeQueries({
        @NamedNativeQuery(name="GET_FOLLOWING_BY_ID", query="SELECT * FROM FOLLOWINGS WHERE PRODUCT_ID = ?", resultClass = Following.class)
})
public class Following implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "FOLLOWING_ID_S")
    @SequenceGenerator(name = "FOLLOWING_ID_S", sequenceName = "FOLLOWING_ID_S", allocationSize = 1)
    @Column(name = "ID")
    private int id;
    @Column(name = "FOLLOWER_ID")
    private int follower_id;
    @Column(name = "PRODUCT_ID")
    private int product_id;

    public Following() {}
    public Following(int id, int follower_id, int product_id) {
        setId(id);
        setFollower_id(follower_id);
        setProduct_id(product_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(int follower_id) {
        this.follower_id = follower_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
