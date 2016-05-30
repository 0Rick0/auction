package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue
    private int id;
    //FontysTime is an simple type(only time in seconds) so it is embedded
    @Embedded
    private FontysTime time;
    //A user can have multiple bids but a bid can only have one user
    @ManyToOne
    private User buyer;
    //Money is a simple type(only amount and currency) so it is embedded
    @Embedded
    private Money amount;
    @OneToOne(orphanRemoval = true, optional = false)
    private Item item;

    public Bid(){}

    public Bid(User buyer, Money amount, Item item) {
        this.buyer = buyer;
        this.amount = amount;
        this.time = FontysTime.now();
        this.item = item;
        //TODO persist?
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return ""+id;
    }

    public boolean equals(Object other){
        if(!(other instanceof Bid))
            return false;
        Bid ob = (Bid)other;
        return ob.getAmount().equals(amount);
    }

}
