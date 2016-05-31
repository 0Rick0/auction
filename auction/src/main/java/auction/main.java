package auction;

import auction.web.*;

import javax.xml.ws.Endpoint;

/**
 * Created by Rick on 31-5-2016.
 */
public class main {

    private static final String auctionUrl = "http://localhost:8080/Auction";
    private static final String registrationUrl = "http://localhost:8080/Registration";

    public static void main(String[] args){
        Endpoint.publish(auctionUrl,new Auction());
        Endpoint.publish(registrationUrl,new Registration());
    }

}
