package pl.polsl.company.model;

import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.polsl.database.entities.Transactions;


public class AuthorizationQueueTest {

    /**
     * Test of add method, of class AuthorizationQueue.
     */
    @Test
    public void testAdd() {
        //given
        RoomRentTransactionForTesting transactionForTesting = new RoomRentTransactionForTesting(null);
        AuthorizableTransaction authorizableTransaction = new AuthorizableTransaction(transactionForTesting);
        AuthorizationQueue authorizationQueue = new AuthorizationQueue();
        //when
        boolean result = authorizationQueue.add(authorizableTransaction);
        //then
        assertTrue("Could not add authorizable transaction to queue.", result);
    }

    /**
     * Test of accept method, of class AuthorizationQueue.
     */
    @Test
    public void testAccept() {
        //given
        Transactions t = new Transactions(new GregorianCalendar(2012, 10, 20), new GregorianCalendar(2012, 11, 21), 1200.00, "FIRM", 29, 0, false);
        RoomRentTransactionForTesting transactionForTesting = new RoomRentTransactionForTesting(t);
        AuthorizableTransaction authorizableTransaction = new AuthorizableTransaction(transactionForTesting);
        AuthorizationQueue authorizationQueue = new AuthorizationQueue();
        authorizationQueue.add(authorizableTransaction);
        //when
        boolean result = authorizationQueue.accept(authorizableTransaction);
        //then
        assertTrue("Could not accept authorizable transaction.", result);
    }

    /**
     * Test of remove method, of class AuthorizationQueue.
     */
    @Test
    public void testRemove() {
        //given
        RoomRentTransactionForTesting transactionForTesting = new RoomRentTransactionForTesting(null);
        AuthorizableTransaction authorizableTransaction = new AuthorizableTransaction(transactionForTesting);
        AuthorizationQueue authorizationQueue = new AuthorizationQueue();
        authorizationQueue.add(authorizableTransaction);
        int authorizableTransactionId = 0;
        //when
        boolean result = authorizationQueue.remove(authorizableTransactionId);
        //then
        assertTrue("Could not remove authorizable transaction from queue.", result);
    }
    
}
