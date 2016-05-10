package auction.domain;

import javax.persistence.*;

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
})
public class User /*implements Comparable*/ {

    @Id @GeneratedValue
    private long id;
    private String email;

    public User(){}

    public User(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
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
/*
    @Override
    public int compareTo(Object o) {
        if(!(o instanceof User))
            return 0;
        return o.hashCode() - hashCode();
    }*/
}
