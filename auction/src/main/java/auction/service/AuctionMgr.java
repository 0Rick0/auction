package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.util.ArrayList;
import java.util.List;

public class AuctionMgr  {

    private ItemDAO itemDAO = new ItemDAOJPAImpl();

   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        return itemDAO.find(id);
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        return itemDAO.findByDescription(description);
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        //get an up-to-date version of the item
        Item nitem = itemDAO.find(item.getId());
        //if the item is not found or there is a higher bid, null is returned
        if(nitem == null || (nitem.getHighestBid()!=null && nitem.getHighestBid().getAmount().compareTo(amount)>0))
                return null;
        //a new bid is created
        Bid newBid = new Bid(buyer,amount,item);
        item.setHighestBid(newBid);
        //the edited item is persisted
        itemDAO.edit(item);
        return newBid;
    }
}
