package auction.web;

import auction.service.*;
import auction.domain.*;
import nl.fontys.util.Money;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by Rick on 31-5-2016.
 */
@WebService
public class Auction {
    AuctionMgr auctionMgr = new AuctionMgr();
    SellerMgr sellerMgr = new SellerMgr();

    public Item getItem(long id){
        return auctionMgr.getItem(id);
    }

    public List<Item> findItemByDescription(String description){
        return auctionMgr.findItemByDescription(description);
    }

    public Bid newBid(Item item, User buyer, Money amount){
        return auctionMgr.newBid(item,buyer,amount);
    }

    public Item offerItem(User seller, Category cat, String description){
        return sellerMgr.offerItem(seller,cat,description);
    }

    public boolean revokeItem(Item item){
        return sellerMgr.revokeItem(item);
    }
}
