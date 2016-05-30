package auction.service;

import org.junit.Ignore;
import javax.persistence.*;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemsFromSellerTest {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Auction");
    final EntityManager em = emf.createEntityManager();
    private AuctionMgr auctionMgr;
    private RegistrationJPAMgr registrationMgr;
    private SellerMgr sellerMgr;

    public ItemsFromSellerTest() {
    }

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationJPAMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
        //new DatabaseCleaner(em).clean();
    }

    // you should create your test with non empty databases in mind, you will not clear a production database but will run test to see if the application runs!

    @Test
 //   @Ignore
    public void numberOfOfferdItems() {

        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        User user1 = registrationMgr.registerUser(email);
        int init = user1.numberOfOfferdItems();
        assertEquals(init + 0, user1.numberOfOfferdItems());

        Category cat = new Category("cat2");
        Item item1 = sellerMgr.offerItem(user1, cat, omsch1);

       
        // test number of items belonging to user1
//        assertEquals(0, user1.numberOfOfferdItems());
        assertEquals(init + 1, user1.numberOfOfferdItems());
        //one user added
        
        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         */
         
         
        assertEquals(init + 1, item1.getSeller().numberOfOfferdItems());


        User user2 = registrationMgr.getUser(email);
        assertEquals(init + 1, user2.numberOfOfferdItems());
        Item item2 = sellerMgr.offerItem(user2, cat, omsch2);
        assertEquals(init + 2, user2.numberOfOfferdItems());

        User user3 = registrationMgr.getUser(email);
        assertEquals(init + 2, user3.numberOfOfferdItems());

        User userWithItem = item2.getSeller();
        assertEquals(init + 2, userWithItem.numberOfOfferdItems());
//        assertEquals(3, userWithItem.numberOfOfferdItems());
        //adding two items, using a different route with different managed objects isn;t going to change that
        //but user is based of item2 with is created with user2 which is not refreshed/updated so it thinks it has 2 items
        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         */
        
        
//        assertNotSame(user3, userWithItem);
        assertEquals(user3, userWithItem);
        //hashes of email will be equal, so Equals will return true

    }

    @Test
//    @Ignore
    public void getItemsFromSeller() {
        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        Category cat = new Category("cat2");

        User user10 = registrationMgr.registerUser(email);

        em.getTransaction().begin();
//        Query q = em.createNamedQuery("User.DeleteAllItems");
//        q.setParameter("userid",user10.getId());
//        q.executeUpdate();
        User userm = em.merge(user10);
        userm.clearItems();

        em.getTransaction().commit();

        Item item10 = sellerMgr.offerItem(user10, cat, omsch1);
        Iterator<Item> it = user10.getOfferedItems();
        // testing number of items of java object
        assertTrue(it.hasNext());
        
        // now testing number of items for same user fetched from db.
        User user11 = registrationMgr.getUser(email);
        Iterator<Item> it11 = user11.getOfferedItems();
        assertTrue(it11.hasNext());
        it11.next();
        assertFalse(it11.hasNext());

        // Explain difference in above two tests for te iterator of 'same' user

        
        
        User user20 = registrationMgr.getUser(email);
        Item item20 = sellerMgr.offerItem(user20, cat, omsch2);
        Iterator<Item> it20 = user20.getOfferedItems();
        assertTrue(it20.hasNext());
        it20.next();
        assertTrue(it20.hasNext());


        User user30 = item20.getSeller();
        Iterator<Item> it30 = user30.getOfferedItems();
        assertTrue(it30.hasNext());
        it30.next();
        assertTrue(it30.hasNext());

    }
}
