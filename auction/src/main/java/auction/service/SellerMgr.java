package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

public class SellerMgr {

    private ItemDAO itemDAO = new ItemDAOJPAImpl();

    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        //create a new item
        Item item = new Item(seller,cat,description);
        itemDAO.create(item);
        return item;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        //check if the item can be removed
        Item fitem = itemDAO.find(item.getId());
        if(fitem==null || fitem.getHighestBid() != null)
            return false;

        //remove the item
        itemDAO.remove(fitem);
        return true;
    }
}
