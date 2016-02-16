package pl.polsl.company.model;

import org.junit.Test;
import static org.junit.Assert.*;


public class ApplicationContextTest {

    /**
     * Test of getAuthorizationQueue method, of class ApplicationContext.
     */
    @Test
    public void testGetAuthorizationQueue() {
        //given
        ApplicationContext applicationContext = new ApplicationContext();
        //when
        AuthorizationQueue result = applicationContext.getAuthorizationQueue();
        //then
        assertNotNull("Could not get authorization queue.", result);
    }

    /**
     * Test of getTransactionList method, of class ApplicationContext.
     */
    @Test
    public void testGetTransactionList() {
        //given
        ApplicationContext applicationContext = new ApplicationContext();
        //when
        TransactionList result = applicationContext.getTransactionList();
        //then
        assertNotNull("Could not get transation list.", result);
    }
    
}
