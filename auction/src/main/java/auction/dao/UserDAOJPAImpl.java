package auction.dao;

import auction.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO {

    private EntityManagerFactory emf;
    public UserDAOJPAImpl() {
        emf = Persistence.createEntityManagerFactory("Auction");
    }

    @Override
    public int count() {
        EntityManager em = emf.createEntityManager();
        return (int)em.createNamedQuery("User.Count").getSingleResult();
    }

    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }


    @Override
    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("User.GetAllUsers");
        return q.getResultList();
    }

    @Override
    public User findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("User.GetUserWithEmail");
        q.setParameter("mail",email);
        try{
            return (User)q.getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public void remove(User user) {
        EntityManager em = emf.createEntityManager();
        em.remove(user);
    }

    public void deleteAll(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("User.DeleteAll");
        q.executeUpdate();
        em.getTransaction().commit();
    }
}
