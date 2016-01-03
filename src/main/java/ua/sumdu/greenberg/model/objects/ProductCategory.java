package ua.sumdu.greenberg.model.objects;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by GREEN on 02.01.2016.
 */
@Entity
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "PRODUCT_CATEGORY_ID_S")
    @SequenceGenerator(name = "PRODUCT_CATEGORY_ID_S", sequenceName = "PRODUCT_CATEGORY_ID_S", allocationSize = 1)
    @Column(name="ID")
    private int id;
    @Column(name = "PRODUCT_ID")
    private int productId;
    @Column(name = "CATEGORY_ID")
    private int categoryId;

    public ProductCategory() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
