package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import auction.domain.*;

public class SellerMgr {

    private ItemDAO itemDAO = new ItemDAOJPAImpl();
    private UserDAO userDAO = new UserDAOJPAImpl();

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
        userDAO.edit(seller);
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

        item.getSeller().removeItem(item);
        userDAO.edit(item.getSeller());

        //remove the item
        itemDAO.remove(fitem);
        return true;
    }

    public Item offerFurniture(User seller, Category cat, String description, String material) {
        //create a new item
        Item item = new Furniture(seller,cat,description,material);
        itemDAO.create(item);
        userDAO.edit(seller);
        return item;
    }

    public Item offerPainting(User seller, Category cat, String description, String title, String painter) {
        //create a new item
        Item item = new Painting(seller,cat,description,title,painter);
        itemDAO.create(item);
        userDAO.edit(seller);
        return item;
    }
}
