package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;

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
    @ManyToOne
    private User seller;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private String description;
    @OneToOne
    private Bid highest;

    public Item(){}

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
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

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    public int compareTo(Object arg0) {
        //TODO
        return -1;
    }

    public boolean equals(Object o) {
        //TODO
        return false;
    }

    public int hashCode() {
        //TODO
        return 0;
    }

    public void setHighestBid(Bid highestBid) {
        this.highest = highestBid;
    }
}
