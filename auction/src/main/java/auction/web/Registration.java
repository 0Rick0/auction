package auction.web;

import auction.domain.User;
import auction.service.RegistrationJPAMgr;

import javax.jws.WebService;

/**
 * Created by Rick on 31-5-2016.
 */
@WebService
public class Registration {
    RegistrationJPAMgr registrationMgr = new RegistrationJPAMgr();

    public User registerUser(String email){
        return registrationMgr.registerUser(email);
    }

    public User getUser(String email){
        return registrationMgr.getUser(email);
    }

}
