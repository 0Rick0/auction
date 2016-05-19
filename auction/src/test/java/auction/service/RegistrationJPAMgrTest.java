package auction.service;

import auction.domain.User;
import nl.fontys.util.FontysTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RegistrationJPAMgrTest {

    private RegistrationJPAMgr registrationMgr;

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationJPAMgr();
    }

    @After
    public void after(){

    }

    @Test
    public void registerUser() {
        User user1 = registrationMgr.registerUser("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = registrationMgr.registerUser("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = registrationMgr.registerUser("xxx2@yyy2");
        assertEquals(user2bis, user2);//the user is obtained from the database, so it is effectively an different object
        //geen @ in het adres
        assertNull(registrationMgr.registerUser("abc"));
    }

    @Test
    public void getUser() {
        User user1 = registrationMgr.registerUser("xxx5@yyy5");
        User userGet = registrationMgr.getUser("xxx5@yyy5");
        assertEquals(userGet, user1);
        assertNull(registrationMgr.getUser("aaa4@bb5"));
        registrationMgr.registerUser("abc");
        assertNull(registrationMgr.getUser("abc"));
    }

    @Test
    public void getUsers() {
        int initsize;
        String eml1 = FontysTime.now().toString() + "xxx8@yyy";
        String eml2 = FontysTime.now().toString() + "xxx9@yyy";
        List<User> users = registrationMgr.getUsers();
        initsize = users.size();
        assertEquals(initsize, users.size());

        User user1 = registrationMgr.registerUser(eml1);//make it unique
        users = registrationMgr.getUsers();
        assertEquals(initsize+1, users.size());
        assertEquals(users.get(initsize), user1);


        User user2 = registrationMgr.registerUser(eml2);
        users = registrationMgr.getUsers();
        assertEquals(initsize+2, users.size());

        registrationMgr.registerUser("abc");
        //geen nieuwe user toegevoegd, dus gedrag hetzelfde als hiervoor
        users = registrationMgr.getUsers();
        assertEquals(initsize+2, users.size());
    }
}
