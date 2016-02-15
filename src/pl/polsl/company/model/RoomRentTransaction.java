package pl.polsl.company.model;

import pl.polsl.database.entities.Transactions;
import pl.polsl.database.manager.DAOManager;
import pl.polsl.database.manager.operations.TransactionsOperations;

/**
 * Created by Krzysztof Stręk on 2016-01-29.
 */
public class RoomRentTransaction extends Transaction {

    public RoomRentTransaction(Transactions t) {
        super(t);
    }

    public int getRoomNumber() {
        return transactionEntity.getRoomNumber();
    }

    @Override
    public void accept() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        transactionEntity.setAccepted(true);
        persist();
    }

    @Override
    public void refuse() {
        TransactionsOperations t = new TransactionsOperations();
        t.setEntityManager(DAOManager.getInstance("kino").getEntityManager());
        t.deleteEntity(transactionEntity);
    }
}
