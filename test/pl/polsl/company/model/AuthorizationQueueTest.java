package pl.polsl.company.model;

import org.junit.Test;
import static org.junit.Assert.*;


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
        RoomRentTransactionForTesting transactionForTesting = new RoomRentTransactionForTesting(null);
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
