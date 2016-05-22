package auction.dao;

import auction.domain.Item;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Rick on 19-5-2016.
 */
public class ItemDAOJPAImpl implements ItemDAO {

    private EntityManagerFactory emf;
    public ItemDAOJPAImpl() {
        emf = Persistence.createEntityManagerFactory("Auction");
    }

    @Override
    public int count() {
        EntityManager em = emf.createEntityManager();
        return (int)em.createNamedQuery("Item.Count").getSingleResult();
    }

    @Override
    public void create(Item item) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Item item) {
        if (find(item.getId()) == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();
    }

    @Override
    public Item find(Long id) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Item.FindId");
        q.setParameter("id",id);
        try{
            return (Item)q.getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public List<Item> findAll() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Item.All");
        return q.getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Item.ByDescription");
        q.setParameter("desc","%"+description+"%");
        return q.getResultList();
    }

    @Override
    public void remove(Item item) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Item.DeleteId");
        q.setParameter("id",item.getId());
        q.executeUpdate();
        em.getTransaction().commit();
    }
}
