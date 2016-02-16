package pl.polsl.company.model;


import org.junit.Test;
import static org.junit.Assert.*;


public class TransactionListTest {

    /**
     * Test of add method, of class TransactionList.
     */
    @Test
    public void testAdd() {
        //given
        Transaction transaction = new RoomRentTransactionForTesting(null);
        TransactionList transactionList = new TransactionList();
        //when
        boolean result = transactionList.add(transaction);
        //then
        assertTrue("Could not add transaction to the list.", result);
    }

    /**
     * Test of remove method, of class TransactionList.
     */
    @Test
    public void testRemove() {
        //given
        Transaction transaction = new RoomRentTransactionForTesting(null);
        TransactionList transactionList = new TransactionList();
        transactionList.add(transaction);
        //when
        boolean result = transactionList.add(transaction);
        //then
        assertTrue("Could not remove transaction from the list.", result);
    }
    
}
