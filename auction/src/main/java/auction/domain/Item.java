package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.Count",            query = "SELECT COUNT(i) FROM Item i"),
        @NamedQuery(name = "Item.All",              query = "SELECT i FROM Item i"),
        @NamedQuery(name = "Item.FindId",           query = "SELECT i FROM Item i WHERE i.id = :id"),
        @NamedQuery(name = "Item.ByDescription",    query = "SELECT i FROM Item i WHERE i.description LIKE :desc"),
        @NamedQuery(name = "Item.DeleteId",         query = "DELETE FROM Item i WHERE i.id = :id")
})
public class Item implements Comparable {

    @Id
    @GeneratedValue
    private Long id;
    //users can have multiple items for sale but an item can only have one user
    @ManyToOne(cascade={CascadeType.REMOVE})
    private User seller;
    //A category can have multiple items bat an item can only have one category
    //A category is never persisted so it is cascaded by Item
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private String description;
    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    //A bid can only have one Item and an item can only have one highest bid.
    private Bid highest;

    public Item(){}

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
        seller.addItem(this);
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

//    public Bid newBid(User buyer, Money amount) {
//        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
//            return null;
//        }
//        highest = new Bid(buyer, amount);
//        return highest;
//    }

    public void setSeller(User seller){
        this.seller=seller;
    }

    public int compareTo(Object arg0) {
        return -1;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Item))
            return false;
        Item oi = (Item)o;
        return Objects.equals(oi.getId(), id);//if id is not the same, the object is not the same
    }

    public int hashCode() {
        if(id == null) return 0;
        return id.intValue();
    }

    public void setHighestBid(Bid highestBid) {
        this.highest = highestBid;
    }
}
