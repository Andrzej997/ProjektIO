package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.TransactionsOperations;

/**
 * Class with room rent transaction object, extends Transaction abstract
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class RoomRentTransaction extends Transaction {

    /**
     * Constructor
     * 
     * @param t Transactions entity object
     */
    public RoomRentTransaction(Transactions t) {
        super(t);
    }

    /**
     * Method to get room number from entity
     * 
     * @return Integer with room number
     */
    public int getRoomNumber() {
        return transactionEntity.getRoomNumber();
    }

    /**
     * Method to set transaction accepted and autorized
     */
    @Override
    public void accept() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        transactionEntity.setAccepted(true);
        this.modify();
        
    }

    /**
     * Method to refuse rooms renting transation
     */
    @Override
    public void refuse() {
        delete();
    }
}
