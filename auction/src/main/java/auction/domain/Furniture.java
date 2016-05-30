package auction.domain;

import javax.persistence.Entity;

/**
 * Created by Rick on 30-5-2016.
 */
@Entity
public class Furniture extends Item {
    private String material;

    public Furniture(){}

    public Furniture(User seller, Category category, String description, String material){
        super(seller,category,description);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}


