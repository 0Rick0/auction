package auction.domain;

import javax.persistence.Entity;

/**
 * Created by Rick on 30-5-2016.
 */
@Entity
public class Painting extends Item {
    private String title;
    private String painter;

    public Painting(){}

    public Painting(User seller, Category category, String description, String title, String painter){
        super(seller, category, description);
        this.title = title;
        this.painter = painter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }
}
