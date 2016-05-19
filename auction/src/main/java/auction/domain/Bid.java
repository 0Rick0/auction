package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue
    private int id;
    @Embedded
    private FontysTime time;
    @ManyToOne
    private User buyer;
    @Embedded
    private Money amount;

    public Bid(){}

    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
        this.amount = amount;
        this.time = FontysTime.now();
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
}
