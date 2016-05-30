package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import nl.fontys.util.Money;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rick on 30-5-2016.
 */
public class BidBidirectionTest {

    static AuctionMgr auctionMgr;
    static RegistrationJPAMgr registrationJPAMgr;
    static SellerMgr sellerMgr;

    @BeforeClass
    public static void before(){
        auctionMgr = new AuctionMgr();
        registrationJPAMgr = new RegistrationJPAMgr();
        sellerMgr = new SellerMgr();
    }

    @Test
    public void test(){
        String seller = "TestBidBi@Mail.nl";
        String description = "Some useless item";
        User uSeller = registrationJPAMgr.registerUser(seller);
        Category category = new Category("TestBidBiCat");
        Item i = sellerMgr.offerItem(uSeller,category,description);
        assertNotNull(i);

        String buyer1 = "BuyerBidBi@Mail.nl";
        User uBuyer1 = registrationJPAMgr.registerUser(buyer1);
        Money amount1 = new Money(100,"EUR");
        List<Item> items = auctionMgr.findItemByDescription(description);
        assertTrue(items.size()>0);
        Bid bid = auctionMgr.newBid(items.get(items.size()-1),uBuyer1,amount1);
        assertNotNull(bid);
        assertEquals(bid.getItem(),i);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Auction");
        EntityManager em = emf.createEntityManager();
        i = em.merge(i);
        em.refresh(i);
        em.close();
        assertEquals(i.getHighestBid().getBuyer(),bid.getBuyer());
    }
}
