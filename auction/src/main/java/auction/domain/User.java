package auction.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.GetAllUsers",
        query = "SELECT u FROM User as u"),
    @NamedQuery(name = "User.Count",
        query = "SELECT count(u) FROM User u"),
    @NamedQuery(name = "User.GetUserWithEmail",
        query = "SELECT u FROM User as u WHERE u.email = :mail"),
    @NamedQuery(name = "User.DeleteAll",
        query = "DELETE FROM User u")
//    @NamedQuery(name = "User.DeleteAllItems",
//        query = "DELETE FROM User.offeredItems i WHERE User.id = :userid")
})
public class User /*implements Comparable*/ {

    @Id @GeneratedValue
    private long id;
    private String email;
    @OneToMany(cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Set<Item> offeredItems;

    public User(){}

    public User(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public Iterator<Item> getOfferedItems(){
        if(offeredItems==null) return null;
        return offeredItems.iterator();
    }

    public int numberOfOfferdItems(){
        if(offeredItems==null)return 0;
        return offeredItems.size();
    }

    boolean addItem(Item item){
        if(offeredItems == null) offeredItems = new HashSet<>();
        return offeredItems.add(item);
    }

    public boolean removeItem(Item item) {
        if(offeredItems == null) return false;
        return offeredItems.remove(item);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User)){
            return super.equals(obj);
        }
        return obj.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    public long getId() {
        return id;
    }

    public void clearItems() {
        offeredItems.clear();
    }
/*
    @Override
    public int compareTo(Object o) {
        if(!(o instanceof User))
            return 0;
        return o.hashCode() - hashCode();
    }*/
}
